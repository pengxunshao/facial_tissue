//vue 组件
Vue.component('search-history',{
  template:`
    <div>
        <div v-if="history&&history.length>0">
            搜索历史
        </div>
        <ul>
            <li v-for="(item,index) in history" :key="index">{{item}}</li>
        </ul>
    </div>
  `,
  props:{
      history:{
          required:true,
          type:Array,
      }
  }
})