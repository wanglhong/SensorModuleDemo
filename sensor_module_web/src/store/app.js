import { defineStore } from "pinia"

export const useAppStore = defineStore({
    id: "app",
    state: () => {
        return {
            // 多选项卡
            tab: true,
            // logo
            logo: true,
            // 菜单层级
            level: true,
            // inverted
            inverted: false,
            routerAlive: true,
            // 菜单折叠
            collapse: false,
            // 菜单分栏
            subfield: false,
            locale: "zh_CN",
            subfieldPosition: "side",
            // 夜间模式（dark、light）
            theme: "light",
            breadcrumb: true,
            sideWidth: "220px",
            sideTheme: "dark",
            // 灰色模式
            greyMode: false,
            // 手风琴
            accordion: false,
            // 选项卡风格(underpainting、concise、designer)
            tagsTheme: "underpainting",
            keepAliveList: [],
            themeVariable: {
                // "--global-checked-color": "#5fb878",
                // "--global-primary-color": "#009688",
                // "--global-normal-color": "#1e9fff",
                // "--global-danger-color": "#ff5722",
                // "--global-warm-color": "#ffb800",
                // "--global-border-radius": "4px"
                "--global-checked-color": "#5fb878",
                "--global-primary-color": "#1e9fff",
                "--global-normal-color": "#009688",
                "--global-danger-color": "#ff5722",
                "--global-warm-color": "#ffb800",
                "--global-border-radius": "4px"
            }
        }
    },
    persist: {
        storage: localStorage,
        paths: [
            "tab",
            "locale",
            "theme",
            "logo",
            "level",
            "inverted",
            "breadcrumb",
            "sideTheme",
            "greyMode",
            "accordion",
            "keepAliveList",
            "themeVariable",
            "subfield",
            "tagsTheme"
        ]
    }
})
