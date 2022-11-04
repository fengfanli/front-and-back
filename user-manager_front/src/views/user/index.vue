<template>
  <div style="height: 100%; margin-top: 10px;">
    <!-- 筛选框 -->
    <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
      <el-select v-model="formInline.startBirth" clearable filterable  placeholder="开始年份" size="mini">
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" style="width: 100">
        </el-option>
      </el-select>
      <el-select v-model="formInline.endBirth" clearable filterable placeholder="结束年份" size="mini">
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"
          style="width: 100px">
        </el-option>
      </el-select>

      <el-form-item label="" clearable size="mini"  class="demo-ruleForm" prop="startRoad" style="margin-left: 10px"
        :rules="[
          { type: 'number', message: '必须为数字值'}
        ]" 
        >
        <el-input type="startRoad" v-model.number="formInline.startRoad" placeholder="里程开始范围"  autocomplete="off" style="width: 110px" ></el-input>
      </el-form-item>
      <el-form-item label="" clearable size="mini"  class="demo-ruleForm" prop="endRoad"
        :rules="[
          { type: 'number', message: '必须为数字值'}
        ]" 
        >
        <el-input type="endRoad" v-model.number="formInline.endRoad" placeholder="里程结束范围" style="width: 110px"></el-input>
      </el-form-item>
      <el-form-item label="" clearable size="mini" prop="startTime"
        :rules="[
          { type: 'number', message: '必须为数字值'}
        ]" 
      >
        <el-input type="startTime" v-model.number="formInline.startTime" placeholder="时间开始范围" style="width: 110px"></el-input>
      </el-form-item>
      <el-form-item label="" clearable size="mini" prop="endTime"
        :rules="[
            { type: 'number', message: '必须为数字值'}
          ]" 
      >
        <el-input type="endTime" v-model.number="formInline.endTime" placeholder="时间结束范围" style="width: 110px"></el-input>
      </el-form-item>
      <el-button type="success" size="mini" @click="search('formInline')">搜索</el-button>
      <el-button type="success" size="mini" @click="resetForm('formInline')">重置</el-button>
    </el-form>

    <!-- 图表 -->
    <el-collapse 
      v-loading="loadingChart" 
      element-loading-text="拼命加载中" 
      element-loading-spinner="el-icon-loading" 
      v-model="activeNames" 
      @change="handleChange" 
      style="margin-bottom: 10px">
      <el-collapse-item title="  统计图表" name="1" >
        <el-tabs v-model="activeName" type="card" style="height: 320px; width: 1000px" @tab-click="handleClick">
          <el-tab-pane label="生日维度" name="first">

            <el-form :inline="true" :model="birthIntervalForm" ref="bitrhInterval" class="demo-form-inline" style="margin-left: 700px">
              <el-form-item label="间隔时间" clearable size="mini" prop="bitrhInterval"
                :rules="[
                    { type: 'number', message: '必须为数字值'}
                  ]" 
              >
                <el-input type="bitrhInterval" v-model.number="birthIntervalForm.bitrhInterval" placeholder="请输入时间间隔" style="width: 120px"></el-input>
                <el-button type="success" size="mini" @click="birthBarSearch('bitrhInterval')" style="margin-left: 10px" >搜索</el-button>
              </el-form-item>
            </el-form>
            <!-- 饼状图 -->
            <div style="height: 240px; width: 1000px" ref="birthdayPie"></div>
          </el-tab-pane>
          <el-tab-pane label="性别维度" name="second">
            <!-- 柱状图 -->
            <div style="height: 240px; width: 1000px" ref="genderBar"></div>
          </el-tab-pane>
          <el-tab-pane label="旅行时间维度" name="third">
            <!-- 折线图 -->
            <div style="height: 240px; width: 1000px" ref="travelTimeLine"></div>
          </el-tab-pane>
        </el-tabs>
      </el-collapse-item>
    </el-collapse>

    <!-- 表格 -->
    <el-table 
      v-loading="loadingTable" 
      element-loading-text="拼命加载中" 
      element-loading-spinner="el-icon-loading" 
      class="c-content" :data="tableList" style="width: 100%" :row-class-name="tableRowClassName">
      <el-table-column prop="id" label="ID" width="210"></el-table-column>
      <!-- 1:男 0： 女 -->
      <el-table-column prop="gender" label="性别" width="210" >
        <template slot-scope="scope">
          <span v-if="scope.row.gender === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column prop="birthday" label="生日" width="210"></el-table-column>
      <el-table-column prop="total_travel_road" label="总旅行公里" width="210"></el-table-column>
      <el-table-column prop="total_travel_time" label="总旅行时间" width="210"></el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block">
      <el-pagination 
        @size-change="handleSizeChange" 
        @current-change="handleCurrentChange" 
        :current-page="pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 100]" 
        :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="totalSize"
        :hide-on-single-page="true">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { getList, getTest, getBirthday, getGenderBarChart, getBirthdayPieChart, getTotalTravelTimeLineChart} from '../../api/data'
import * as echarts from 'echarts'

export default {
  name: 'user',
  data() {
    return {
      formInline: {
        startBirth: null,
        endBirth: null,
        startRoad: null,
        endRoad: null,
        startTime: null,
        endTime: null
      },
      birthIntervalForm: {  // 生日 饼状图中表格 时间间隔
        bitrhInterval: null
      }, 
      numberRules: [
         { type: 'number', message: '年龄必须为数字值'}
      ],
      options: [],
      allYear:[], // 年份的去重所有数据
      value: '',
      // 分页数据
      tableData: [],  // 所有数据
      tableList: [],  // 当前数据

      pageSize: 10,   // 页面数据条数
      pageNum: 1,      // 当前页
      totalSize: null, // 总数据条数

      // 图表
      activeNames: [], //['1']
      // tab
      activeName: 'first',
      loadingChart: false,
      loadingTable: false,
    }
  },
  mounted() {
    // 获取数据列表
    this.loadingTable = true
    setTimeout(() => {
      this.getData()
      // 获取年份
      this.getBirth()
      // 性别维度，柱状图
      this.getGenderBar()
      // 生日维度，饼状图
      this.getBirthdayPie()
      // 旅行时间维度 折线图
      this.getTotalTravelTimeLine()
      this.loadingTable = false
    }, 1000)
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    onSubmit() {
      console.log('submit!');
    },
    // 选择每页多少条时，触发事件
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val
      this.loadingTable = true
      setTimeout(() => {
        this.getData()
        this.loadingTable = false
      }, 1000)
    },
    // 选择当前 某页 时，触发事件
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadingTable = true
      setTimeout(() => {
        this.getData()
        this.loadingTable = false
      }, 1000)
    },
    getData(search) {
      let param = {}
      if(search === 'search'){
        param = {
          ...this.formInline,
          'pageNum': 1,
          'pageSize': 10
        }
      } else {
        param = {
          ...this.formInline,
          'pageNum': this.pageNum,
          'pageSize': this.pageSize
        }
      }
      
      // console.log('getData:', param)
      getList(param).then(res => {
        // console.log('allData:',res)
        const { data, total, code } = res.data
        if(code == 200){
          // this.tableData = data
          this.totalSize = total     // 后端分页-返回数据总条数
          this.tableList = data      // 后端分页-返回页面数据
          // 前端分页
          // this.pageList()
        }
      })
    },
    // 前段分页的方法
    pageList() {
      // 获取当前页 给 tableList 去页面展示
      this.tableList = this.tableData.filter(
        (item, index) =>
          index < this.pageNum * this.pageSize &&
          index >= this.pageSize * (this.pageNum - 1)
      );
    },
    search(formInline) {
      this.$refs[formInline].validate((valid) => {
        // console.log('search validate', valid)
        if (valid) {
          this.loadingChart = true
          this.loadingTable = true
          setTimeout(() => {
            this.getData('search')
            this.getGenderBar()
            this.getBirthdayPie()
            this.getTotalTravelTimeLine()
            this.loadingChart = false
            this.loadingTable = false
          }, 1000);
        } else {
          this.$message({
            message: '筛选条件输入有误，请检查后重新输入!!!',
            type: 'warning'
          });
          return false;
        }
      });
      
    },
    resetForm(formInline){
       this.$refs[formInline].resetFields();
    },
    birthBarSearch(bitrhInterval){
      if(this.formInline.startBirth === null || this.formInline.endBirth === null) {
        this.$message({
          message: '起始年份不能为空,请输入起始年份',
          type: 'warning'
        });
        return false;
      }
      this.$refs[bitrhInterval].validate((valid) => {
        console.log('search validate', valid)
        if (valid) {
          // 只更新饼状图
          this.loadingChart = true
          setTimeout( () => {
            this.getBirthdayPie() 
            this.loadingChart = false
          }, 1000)
        } else {
          this.$message({
            message: '间隔输入有误,请重新输入!!!',
            type: 'warning'
          });
          return false;
        }
      });
    },
    getBirth() {
      getBirthday().then(res => {
        // console.log('birth年份:', res)
        const data = res.data.data
        
        let array = []
        for (let i = 0; i < data.length; i++) {
          array.push({
            value: data[i],
            label: data[i]
          })
          this.allYear.push(data[i])
        }
        this.options = array
        // console.log(res)
      })
    },
    // 图表
    handleChange(val) {
        // console.log(val);
    },
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    // 性别维度，柱状图
    getGenderBar(){
      const param = {
        ...this.formInline
      }
      // console.log('getGenderBar:', param)
      getGenderBarChart(param).then(res => {
        // console.log('bar:',res)
        const {code, data} = res.data
        if(code === 200){
          const xArray = [data[0].gender === 1 ? '男': '女', data[1].gender === 1 ? '男': '女']
          const yArray = [data[0].num, data[1].num]
          this.zhuzhuangtu(xArray, yArray)
        }
      })
    },
    zhuzhuangtu(xArray, yArray){
      const barOption = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: xArray
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '性别维度',
            data: yArray,
            type: 'bar'
          }
        ]
      };

      const e = echarts.init(this.$refs.genderBar)
      e.setOption(barOption)
    },
    // 生日 饼状图
    getBirthdayPie(){
      const param = {
        ...this.formInline,
        'birthInterval': this.birthIntervalForm.bitrhInterval
      }
      // console.log('getBirthdayPie param:',param)
      getBirthdayPieChart(param).then(res => {
        const {code, data} = res.data
        if(code === 200){
          // console.log('getBirthdayPieChart:', data)
          this.bingzhuangtu(data)
        }
      })
    },
    bingzhuangtu(data){
      let array = []
      let l = data.length;
      if(data.length > 50) l =50
      for(let i = 0; i < l ; i++){
        array.push({
          name: data[i].birthday,
          value: data[i].num
        })
      }
      const pieOption = {
        title: {
          text: '生日时间维度',
          subtext: '年龄',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: array,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      const e = echarts.init(this.$refs.birthdayPie)
      e.setOption(pieOption)
    },
    getTotalTravelTimeLine(){
      const param = {
        ...this.formInline
      }
      getTotalTravelTimeLineChart(param).then(res => {
        const {code, data} = res.data
        if(code === 200){
          // console.log('getTotalTravelTimeLineChart:', data)
          this.zhexiantu(data)
        }
      })
    },
    // 注意：就一条线
    zhexiantu(data){
      let timeNums = [] // x 轴：旅行时间
      let nums = []     // y 轴：旅行时间 个数
      for(let i = 0; i < data.length; i++){
        timeNums.push(data[i].totalTravelTime)
        nums.push(data[i].num)
      }

      const lineOption = {
        title: {
          text: '旅行时间维度',
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          // data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
          data: 'people'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          data: timeNums
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: 'people',
          type: 'line',
          stack: 'Total',
          data: nums
        }]
      }
      const e = echarts.init(this.$refs.travelTimeLine)
      e.setOption(lineOption)
    },
  },

}
</script>
<style lang="less" scoped>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

.t-content {
  margin-bottom: 10px;
}

.el-input--mini .el-input__inner {
  width: 100px;
}
// 覆盖 elementUI 样式
/deep/ .el-collapse-item__header.is-active{
  border-bottom-color: transparent;
  padding-left: 500px;
}
/deep/ .el-collapse-item__header {
    display: flex;
    align-items: center;
    height: 48px;
    line-height: 48px;
    background-color: #FFF;
    color: #303133;
    cursor: pointer;
    border-bottom: 1px solid #EBEEF5;
    font-size: 13px;
    font-weight: 500;
    transition: border-bottom-color .3s;
    outline: 0;
    padding-left: 500px;
}
</style>