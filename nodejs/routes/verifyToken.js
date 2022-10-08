import jwt from 'jsonwebtoken'

function auth(req, res, next){
    const token = req.header('auth_token')
    if(!token) return res.status(402).send("Access denied.")

    try{
        const verified = jwt.verify(token, process.env.TOKEN_SECRET)
        req.user = verified
        next()
    } catch(error){
        res.status(400).send('Invalid token.')
    }
}

export default auth