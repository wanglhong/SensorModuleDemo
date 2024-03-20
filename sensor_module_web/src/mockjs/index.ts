// @ts-ignore
import Mock from'mockjs';
import user from './user';

Mock.mock(/\/api\/upms\/sys\/login/,'post',(req: any,res: any) =>{
    return user.getLogin(req,res)
});

Mock.mock(/\/api\/upms\/sys\/info/,'post',(req: any,res: any) =>{
    return user.getInfo(req,res)
});

Mock.mock(/\/api\/upms\/sys\/menu/,'get',(req: any,res: any) =>{
    return user.getMenu(req,res)
});

Mock.mock(/\/api\/upms\/sys\/permission/,'get',(req: any,res: any) =>{
    return user.getPermission(req,res)
});

Mock.mock(/\/file\/upload/,'post',(req: any,res: any) =>{
    return user.getUpload(req,res)
}); 

export default Mock;