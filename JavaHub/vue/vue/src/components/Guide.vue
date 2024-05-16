<template>
  <div>
  <el-steps :active="active" finish-status="success" style="width:100vh;">
    <el-step title="Step 1" :active="active1"/>
    <el-step title="Step 2" />
    <el-step title="Step 3" />
  </el-steps>
  </div>

  <div class="col-12" v-if="step1">
    <div class="card">
          <p>
            <img :src="guide1" />
            <br>
            <img :src="guide2" style="align:right"/>
          </p>

    </div>
  </div>
  <div class="col-12" v-if="step2">
    <div>
      <Toast />

      <div class="card">
        <h5>Java开发学习</h5>
        <OrganizationChart :value="data1" :collapsible="true" class="company" selectionMode="single" v-model:selectionKeys="selection"
                           @nodeSelect="onNodeSelect" @nodeUnselect="onNodeUnselect" @nodeCollapse="onNodeCollapse" @nodeExpand="onNodeExpand">
          <template #person="slotProps">
            <div class="node-header ui-corner-top">{{slotProps.node.data.label}}</div>
            <div class="node-content">
              <div>{{slotProps.node.data.name}}</div>
            </div>
          </template>
          <template #default="slotProps">
            <span>{{slotProps.node.data.label}}</span>
          </template>
        </OrganizationChart>
      </div>
    </div>
  </div>
  <div class="col-12" v-if="step3">
    <div class="card">
      <p>
        <img :src="guide3" />
        <br>
        <img :src="guide5" />
        <br>
        <img :src="guide4" />
        <img :src="guide6" style="width:450px"/>
      </p>
    </div>
  </div>
  <el-button style="margin-top: 12px" @click="next">Next step</el-button>
</template>

<script>
import guide11 from '@/assets/img/guide1.jpg'
import guide21 from '@/assets/img/guide2.jpg'
import guide31 from '@/assets/img/guide3.jpg'
import guide41 from '@/assets/img/guide4.jpg'
import guide51 from '@/assets/img/guide5.jpg'
import guide61 from '@/assets/img/guide6.jpg'
export default {
  name:"guide",
  data(){
    return{
      active: 0,
      active1:1,
      guide1:guide11,
      guide2:guide21,
      guide3:guide31,
      guide4:guide41,
      guide5:guide51,
      guide6:guide61,
      step1:true,
      step2:false,
      step3:false,
      data1: {
        key: '0',
        type: 'person',
        styleClass: 'p-person',
        data: {label: 'Java开发'},
        children: [
          {
            key: '0_0',
            type: 'person',
            styleClass: 'p-person',
            data: {label: '基础程序设计'},
            children:[{
              key: '0_0_0',
              data: {label: '关键字'},
              selectable: false,
              styleClass: 'department-cfo'
            },
              {
                key: '0_0_1',
                data: {label: '数据类型'},
                selectable: false,
                styleClass: 'department-cfo'
              },
              {
                key: '0_0_2',
                data: {label: '…'},
                selectable: false,
                styleClass: 'department-cfo'
              },
            ],
          },
          {
            key: '0_1',
            type: 'person',
            styleClass: 'p-person',
            data: {label: 'Java新特性'},
            children:[{
              key: '0_1_0',
              data: {label: '泛型'},
              selectable: false,
              styleClass: 'department-coo'
            },
              {
                key: '0_1_1',
                data: {label: '元注解'},
                selectable: false,
                styleClass: 'department-coo'
              },
              {
                key: '0_1_2',
                data: {label: '…'},
                selectable: false,
                styleClass: 'department-coo'
              },
            ]
          },
          {
            key: '0_2',
            type: 'person',
            styleClass: 'p-person',
            data: {label: '面向对象编程'},
            children:[{
              key: '0_2_0',
              data: {label: '类或对象'},
              selectable: false,
              styleClass: 'department-cto',
            },
              {
                key: '0_2_1',
                data: {label: '类的结构'},
                selectable: false,
                styleClass: 'department-cto'
              },
              {
                key: '0_2_2',
                data: {label: '三大特性'},
                selectable: false,
                styleClass: 'department-cto'
              },
              {
                key: '0_2_3',
                data: {label: '接口'},
                selectable: false,
                styleClass: 'department-cto'
              },
              {
                key: '0_2_4',
                data: {label: '设计模式'},
                selectable: false,
                styleClass: 'department-cto'
              }]
          }
        ]
      },
      selection: {},
    }
  },
methods: {
  next() {
    if (this.active++ > 2){
      this.active = 0;
      this.$router.push('/');
    }
    if(this.active!=0){
      this.step1=false;
    }
    if(this.active==1){
      this.step2=true;
    }else{
      this.step2=false;
    }
    if(this.active==2){
      this.step3=true;
    }else{
      this.step3=false;
    }
  },
  onNodeSelect(node) {
    this.$toast.add({severity:'success', summary: 'Node Selected', detail: node.data.label, life: 3000});
  },
  onNodeUnselect(node) {
    this.$toast.add({severity:'success', summary: 'Node Unselected', detail: node.data.label, life: 3000});
  },
  onNodeExpand(node) {
    this.$toast.add({severity:'success', summary: 'Node Expanded', detail: node.data.label, life: 3000});
  },
  onNodeCollapse(node) {
    this.$toast.add({severity:'success', summary: 'Node Collapsed', detail: node.data.label, life: 3000});
  }
}
}

</script>

<style scoped lang="scss">
::v-deep(b) {
  display: block;
}

::v-deep(.p-card-body) {
  padding: 2rem;
}

::v-deep(.p-organizationchart) {
  .p-person {
    padding: 0;
    border: 0 none;
    background-color: #495ebb;
  }

  .node-header, .node-content {
    padding: .5em .7rem;
    background-color: #495ebb;
  }

  .node-header {
    background-color: #495ebb;
    color: #ffffff;
  }

  .node-content {
    text-align: center;
    border: 1px solid #495ebb;
  }

  //.node-content img {
  //  border-radius: 50%;
  //}

  .department-cfo {
    background-color: #7247bc;
    color: #ffffff;
  }

  .department-coo {
    background-color: #a534b6;
    color: #ffffff;
  }

  .department-cto {
    background-color: #e9286f;
    color: #ffffff;
  }
}
</style>