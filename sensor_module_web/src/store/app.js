import { defineStore } from "pinia"

export const useAppStore = defineStore({
    id: "app",
    state: () => {
        return {
            tab: true,
            logo: true,
            level: true,
            inverted: false,
            routerAlive: true,
            collapse: false,
            subfield: true,
            locale: "zh_CN",
            subfieldPosition: "side",
            theme: "light",
            breadcrumb: true,
            sideWidth: "220px",
            sideTheme: "dark",
            greyMode: false,
            accordion: false,
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
