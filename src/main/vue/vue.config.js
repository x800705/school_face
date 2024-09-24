/*
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    devServer:{  //用于开发环境
        host: 'localhost',
        port: 8080,
    }
})
*/

const { defineConfig } = require('@vue/cli-service')
const webpack = require('webpack')
module.exports = defineConfig({
    transpileDependencies: true,
    configureWebpack: { // 解决 buffer问题
        plugins: [
            new webpack.ProvidePlugin({
                process: 'process/browser',
                Buffer: ['buffer', 'Buffer']
            })
        ]
    }
})
