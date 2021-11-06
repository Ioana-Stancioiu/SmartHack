if (process.env.NODE_ENV !== 'production') {
    require('dotenv').config()
}

const { urlencoded } = require('express')
const express = require('express')
const path = require('path')
const app = express()
const bcrypt = require('bcrypt')
const passport = require('passport')
const flash = require('express-flash')
const session = require('express-session')
const methodOverride = require('method-override')

const initializePassport = require('./passport-config')
initializePassport(
    passport, 
    email => users.find(user => user.email === email),
    id => users.find(user => user.id === id)
)

const users = []

app.set('view-engine', 'ejs')
app.use(urlencoded({ extended: false }))
app.use(express.static(__dirname + '/public'))
app.use(session({
    secret: process.env.SESSION_SECRET,
    resave: false,
    saveUninitialized: false
}))
app.use(flash())

app.use(passport.initialize())
app.use(passport.session())
app.use(methodOverride('_method'))

app.get('/', checkNotAuthenticated, (req, res) => {
    res.render(path.join(__dirname + '/public/login.ejs'))
})

app.post('/', checkNotAuthenticated, passport.authenticate('local', {
    successRedirect: '/buna',
    failureRedirect: '/',
    failureFlash: true
}))

app.get('/register', checkNotAuthenticated, (req, res) => {
    res.render(path.join(__dirname + '/public/register.ejs'))
})

app.post('/register', checkNotAuthenticated, async (req, res) => {
    try {
        const hashedPass = await bcrypt.hash(req.body.password, 10)
        users.push({
            id: Date.now().toString(),
            name: req.body.name,
            email: req.body.email,
            password: hashedPass
        })
        res.redirect('/')
    } catch {
        res.redirect('/register')
    }
    console.log(users)
})

app.delete('/logout', (req, res) => {
    req.logOut()
    res.redirect('/')
})

app.get('/buna', checkAuthenticated, (req, res) => {
    res.render(path.join(__dirname + '/public/buna.ejs'), { name: req.user.name }) 
})

function checkAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return next()
    }

    res.redirect('/')
}

function checkNotAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return res.redirect('/buna')
    }

    return next()
}

app.listen(3000)