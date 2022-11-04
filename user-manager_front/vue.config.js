const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,                        // 关闭 ESlint
  devServer: {
    // https: true,                            // 开启 https 模式
    proxy: {                                // 配置服务器代理
      '/api': {                             // 匹配访问路径中含有 '/api' 的路径
        target: 'http://localhost:8087/api/',   // 测试地址、目标地址
        pathRewrite: {                      // 匹配所有的以/api开头的换成 ''
          '^/api':''
        }, 
        ws: true,                           // 用于支持websocket
        changeOrigin: true,                 // 伪装同源
      }
    }
  }
})
