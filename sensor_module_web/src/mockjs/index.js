// @ts-ignore
import Mock from "mockjs"
import user from "@/mockjs/user.js"

Mock.mock(/\/api\/upms\/sys\/login/, "post", (req, res) => {
    return user.getLogin(req, res)
})

Mock.mock(/\/api\/upms\/sys\/info/, "post", (req, res) => {
    return user.getInfo(req, res)
})

Mock.mock(/\/api\/upms\/sys\/menu/, "get", (req, res) => {
    return user.getMenu(req, res)
})

Mock.mock(/\/api\/upms\/sys\/permission/, "get", (req, res) => {
    return user.getPermission(req, res)
})

Mock.mock(/\/file\/upload/, "post", (req, res) => {
    return user.getUpload(req, res)
})

export default Mock
