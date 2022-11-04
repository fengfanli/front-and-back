import axios from './axios'

export const getList = (param) => {
  return axios.request({
    url: '/api/user/list',
    method: 'post',
    data: param
  })
};

export const getTest = () => {
  return axios.request({
    url: '/api/user/test1'
  })
};

export const getBirthday = () => {
  return axios.request({
    url: '/api/user/birthday'
  })
};

// 性别维度 柱状图
export const getGenderBarChart = (param) => {
  return axios.request({
    url: '/api/user/genderBar',
    method: 'post',
    data: param
  })
};

// 生日维度 饼状图
export const getBirthdayPieChart = (param) => {
  return axios.request({
    url: '/api/user/birthdayPie',
    method: 'post',
    data: param
  })
};

// 旅行时间维度 折现图
export const getTotalTravelTimeLineChart = (param) => {
  return axios.request({
    url: '/api/user/travelTimeLine',
    method: 'post',
    data: param
  })
};