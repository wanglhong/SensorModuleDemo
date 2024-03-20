import { defineStore } from 'pinia'
import { menu, permission } from "../api/module/loginController";

export const useUserStore = defineStore({
  id: 'user',
  state: () => {
    return {
      token: '',
      userInfo: {},
      permissions: [],
      menus: [],
    }
  },
  actions: {
    async loadMenus(){
      const { data, code } = await menu();
      if(code == 200) {
        console.log("menus --> " + JSON.stringify(data));
        this.menus = data;
      }
    },
    async loadPermissions(){
      const { data, code } = await permission();
      if(code == 200) {
        console.log("permissions --> " + JSON.stringify(data));
        this.permissions = data;
      }
    }
  },
  persist: {
    storage: localStorage,
    paths: ['token', 'userInfo', 'permissions', 'menus' ],
  }
})