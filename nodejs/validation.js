import Joi from 'joi'

//Register validation
const registerVal = (data) =>{
    const schema = Joi.object({
        username: Joi.string().min(6).max(30).required(),
        email: Joi.string().min(6).required().email(),
        phone: Joi.string().min(9).max(12),
        password: Joi.string().min(6).required(),
        profile_link: Joi.string()
    })
    return schema.validate(data)
}

//Login validation
const loginVal = (data) =>{
    const schema = Joi.object({
        email: Joi.string().min(6).required().email(),
        password: Joi.string().min(6).required()
    })
    return schema.validate(data)
}

//Token val
const tokenVal = (data) => {
    const schema = Joi.object({
        token: Joi.string().required()
    })
    return schema.validate(data)
}

//Update user data
const userDataVal = (data) =>{
    const schema = Joi.object({
        email: Joi.string().min(6).required().email(),
        username: Joi.string().min(6).max(30).required(),
        phone: Joi.string().min(9).max(12)
    })

    return schema.validate(data)
} 

//Create comment validation
const commentDataVal = (data) =>{
    const schema = Joi.object({
        lat: Joi.number().required().min(-90).max(90),
        lng: Joi.number().required().min(-180).max(180),
        content: Joi.string().min(20).max(255).required(),
        rating: Joi.number().required().min(1).max(5)
    })

    return schema.validate(data)
}

//Place data validation
const latLngDataVal = (data) =>{
    const schema = Joi.object({
        lat: Joi.number().required().min(-90).max(90),
        lng: Joi.number().required().min(-180).max(180)
    })

    return schema.validate(data)
}

export {loginVal, registerVal, tokenVal, userDataVal, commentDataVal, latLngDataVal}