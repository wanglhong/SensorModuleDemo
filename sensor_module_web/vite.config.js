// import { fileURLToPath, URL } from 'node:url';
import { resolve } from "path";

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { LayuiVueResolver } from 'unplugin-vue-components/resolvers';

const excludeComponents = ['LightIcon','DarkIcon']

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    // alias: {
    //   '@': fileURLToPath(new URL('./src', import.meta.url))
    // }
    alias: [
      {
        find: '@',
        replacement: resolve(__dirname, './src')
      }
    ]
  },
  plugins: [AutoImport({
    resolvers: [
        LayuiVueResolver(),
      ],
    }),
    Components({
      resolvers: [
        LayuiVueResolver({
          resolveIcons: true,
          exclude: excludeComponents
        }),
      ],
    }),
    vue()
  ]
})
