const LocalStrategy = require('passport-local').Strategy
const bcrypt = require('bcrypt')

function initialize(passport, getUserByEmail, getUserById) {

    const authenticateUser = async (email, password, done) => {
        const user = getUserByEmail(email)
        console.log('SUNT EGAL');
        if (user == null) {
            return done(null, false, { message: 'No user with that email'})
        }
        console.log('SUNT EGAL');
        try {
            if (await bcrypt.compare(password, user.password)) {
                console.log('SUNT EGAL');
                return done(null, user)
            } else {
                console.log(' nu SUNT EGAL');
                return done(null, false, {message: 'Password incorrect' })
            }
        } catch (e) {
            return done(e)
        }
    }
    passport.use(new LocalStrategy({ usernameField: 'email' }, 
    authenticateUser))
    passport.serializeUser((user, done) => done(null, user.id))
    passport.deserializeUser((id, done) => { 
        return done(null, getUserById(id))
    })
}

module.exports = initialize