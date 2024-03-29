let user = {
    userId: "1992",
    username: "admin"
}

const menus = [
    {
        id: "/baseInfo",
        icon: "layui-icon-home",
        title: "基础信息管理",
        children: [
            {
                id: "/baseInfo/goods",
                icon: "layui-icon-util",
                title: "货物管理"
            },
            {
                id: "/baseInfo/turnoverBox",
                icon: "layui-icon-util",
                title: "周转箱管理"
            },
            {
                id: "/baseInfo/transportEquipment",
                icon: "layui-icon-util",
                title: "运输设备管理"
            }
        ]
    },
    {
        id: "/transportManagement",
        icon: "layui-icon-unlink",
        title: "运输管理",
        children: [
            {
                id: "/transportManagement/transportInfo",
                icon: "layui-icon-not-found",
                title: "运输信息管理"
            },
            {
                id: "/transportManagement/goodsToTurnoverBox",
                icon: "layui-icon-not-found",
                title: "货物装箱"
            },
            {
                id: "/transportManagement/urnoverBoxTotransportEquipment",
                icon: "layui-icon-not-found",
                title: "货物装车"
            }
        ]
    },
    {
        id: "/customInfo",
        icon: "layui-icon-unlink",
        title: "通关信息管理",
        children: [
            {
                id: "/customInfo/customsDeclarationInfo",
                icon: "layui-icon-not-found",
                title: "报关信息管理"
            },
            {
                id: "/customInfo/customsClearanceInfo",
                icon: "layui-icon-not-found",
                title: "清关信息管理"
            }
        ]
    },
    {
        id: "/customApproval",
        icon: "layui-icon-unlink",
        title: "通关审批管理",
        children: [
            {
                id: "/customApproval/customsDeclarationApproval",
                icon: "layui-icon-not-found",
                title: "报关审批管理"
            },
            {
                id: "/customApproval/customsClearanceApproval",
                icon: "layui-icon-not-found",
                title: "清关审批管理"
            }
        ]
    },
    {
        id: "/error",
        icon: "layui-icon-unlink",
        title: "异常页面",
        children: [
            {
                id: "/error/403",
                icon: "layui-icon-not-found",
                title: "403"
            },
            {
                id: "/error/404",
                icon: "layui-icon-not-found",
                title: "404"
            },
            {
                id: "/error/500",
                icon: "layui-icon-not-found",
                title: "500"
            }
        ]
    }
]

const getInfo = (req, res) => {
    let item = JSON.parse(req.body)
    let token = item ? item.token : null
    let result = {
        code: 200,
        msg: "操作成功",
        data: user,
        success: true
    }
    if (item || token) {
        result.code = 99998
        result.msg = "请重新登录"
        result.success = false
    }
    return result
}

const getPermission = (req, res) => {
    let item = JSON.parse(req.body)
    let token = item ? item.token : null
    let result = {
        code: 200,
        msg: "操作成功",
        data: [
            "sys:user:add",
            "sys:user:edit",
            "sys:user:delete",
            "sys:user:import",
            "sys:user:export"
        ],
        success: true
    }
    if (item || token) {
        result.code = 99998
        result.msg = "请重新登录"
        result.success = false
    }
    return result
}

const getMenu = (req, res) => {
    let item = JSON.parse(req.body)
    let token = item ? item.token : null
    let result = {
        code: 200,
        msg: "操作成功",
        data: menus,
        success: true
    }
    if (item || token) {
        result.code = 99998
        result.msg = "请重新登录"
        result.success = false
    }
    return result
}

const getLogin = (req, res) => {
    let item = JSON.parse(req.body)
    let loginName = item.loginName
    let password = item.password
    if (loginName === "admin" && password === "123456") {
        return {
            success: true,
            code: 200,
            msg: "登陆成功",
            data: {
                userId: "35002",
                token:
                    "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiJhZG1pbiIsInVzZXJOYW1lIjoiYWRtaW4iLCJvcmdDb2RlIjoiMzUwMDAiLCJkZXB0Q29kZSI6IjM1MDAwIiwiYXVkIjoiYWRtaW4iLCJpc3MiOiJhZG1pbiIsImV4cCI6MTU5MzUzNTU5OH0.0pJAojRtT5lx6PS2gH_Q9BmBxeNlgBL37ABX22HyDlebbr66cCjVYZ0v0zbLO_9241FX9-FZpCkEqE98MQOyWw"
            }
        }
    } else {
        return {
            success: false,
            code: 500,
            msg: "登陆失败,账号密码不正确"
        }
    }
}

const getUpload = (req, res) => {
    return {
        code: 200,
        msg: "上传成功",
        success: true
    }
}

export default {
    getInfo,
    getMenu,
    getLogin,
    getPermission,
    getUpload
}
