import axios from 'axios'
import config from '../config'

// 判断当前的运行环境, 如果是开发，则取开发地址 否则 取生产地址 
const baseUrl = process.env.NODE_ENV == 'development' ? config.baseUrl.dev : config.baseUrl.pro

// 工具类， ES6 的class语法   ...: ES6的扩展运算符
class HttpRequest {
  constructor(baseUrl) {
    this.baseUrl = baseUrl
  }

  // 配置URL
  getInsideConfig() {
    const config = {
      baseURL: this.baseUrl,
      header: {}
    }
    return config
  }

  interceptors(instance) {
    // 添加请求拦截器
    instance.interceptors.request.use(function (config) {
      // 在发送请求之前做些什么
      // console.log('config:', JSON.stringify(config))
      config.headers.token = 'token_content';
      return config;
    }, function (error) {
      // 对请求错误做些什么
      return Promise.reject(error);
    });

    // 添加响应拦截器
    instance.interceptors.response.use(function (response) {
      // 2xx 范围内的状态码都会触发该函数。
      // 对响应数据做点什么
      return response;
    }, function (error) {
      // 超出 2xx 范围的状态码都会触发该函数。
      // 对响应错误做点什么
      return Promise.reject(error);
    });
  }

  // 调用的函数
  request(options){
    const instance = axios.create()
    // console.log(JSON.stringify('options:',options))

    options = {...this.getInsideConfig(), ...options}

    // console.log('this.baseUrl:',JSON.stringify(this.baseUrl))
    // console.log('this.getInsideConfig:',JSON.stringify(this.getInsideConfig()))
    // console.log('options:',JSON.stringify(options))

    this.interceptors(instance)
    return instance(options)
  }
}

//将 实例化的类 抛出去
export default new HttpRequest(baseUrl)
