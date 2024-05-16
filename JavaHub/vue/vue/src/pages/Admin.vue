<template>
<!--    <div style="height:50px;line-height:50px;border-bottom:1px solid #ccc;display:flex">-->
<!--    <div style="width:200px;padding-left:30px;font-size:25px;font-weight:bold;">后台管理</div>-->
<!--    <div style="flex:1"></div>-->
<!--    <div style="width:200px;margin-top: 15px">-->
<!--      <el-dropdown :hide-on-click="false">-->
<!--            <span class="el-dropdown-link" >-->
<!--              账号管理<el-icon class="el-icon&#45;&#45;right"><arrow-down /></el-icon>-->
<!--            </span>-->
<!--            <template #dropdown>-->
<!--              <el-dropdown-menu>-->
<!--                <el-dropdown-item >退出系统<router-link :to="'/login'" ></router-link></el-dropdown-item>-->
<!--              </el-dropdown-menu>-->
<!--            </template>-->
<!--          </el-dropdown>-->
<!--    </div>-->
<!--    </div>-->
  <el-container>
<!--    <aside>-->
<!--    <div>-->
<!--      <el-menu-->
<!--        style="width:200px;min-height: calc(100vh - 50px)"-->
<!--        default-active="2"-->
<!--        class="el-menu-vertical-demo">-->
<!--        <el-menu-item index="1">-->
<!--          <template #title>用户管理</template>-->
<!--        </el-menu-item>-->
<!--        <el-submenu index="2">-->
<!--          <template #title>知识库管理</template>-->
<!--          <el-menu-item index="2-1">章节管理</el-menu-item>-->
<!--          <el-menu-item index="2-2">知识管理</el-menu-item>-->
<!--          <el-menu-item index="2-3">习题管理</el-menu-item>-->
<!--        </el-submenu>-->
<!--      </el-menu>-->
<!--    </div>-->
<!--    </aside>-->
    <el-main>
<!--      功能区域-->
    <div style="margin: 15px;">
      <el-button @click="add">新增</el-button>
      <el-upload action="/api/userl/import" :show-file-list=false accept="xlsx" :on-success="ImportSuccess" style="display:inline-block">
        <el-button class="ml-5">导入</el-button>
      </el-upload>
      <el-button @click="exp" class="ml-5">导出</el-button>
    </div>
<!--      搜索区域-->
      <div style="margin: 15px">
        <el-input v-model="search" placeholder="请输入内容" style="width:25%" clearable></el-input>
<!--        <el-button style="margin-left:15px" @click="handleSearch(this.search)">搜索</el-button>-->
      </div>
  <div>
    <el-table :data="tableData.slice((paginations.currentPage-1)*paginations.PageSize,paginations.currentPage*paginations.PageSize)" border stripe style="width: 100vw ;margin:10px">
      <el-table-column prop="userid" label="userid" sortable />
      <el-table-column prop="username" label="username"  />
      <el-table-column prop="gender" label="gender"  />
      <el-table-column prop="school" label="school"  />
      <el-table-column prop="grade" label="grade" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="Edit(scope.row)">编辑</el-button>
          <el-popconfirm title="是否确认删除？" @confirm="Delete(scope.row.userid)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
<!--    <TreeTable :value="nodes" :paginator="true" :rows="10">-->
<!--      <Column field="userid" header="userid" :expander="true"></Column>-->
<!--      <Column field="username" header="username"></Column>-->
<!--      <Column field="gender" header="gender"></Column>-->
<!--      <Column field="school" header="school"></Column>-->
<!--      <Column field="grade" header="grade"></Column>-->
<!--      <Column field="操作" header="操作">-->
<!--        <template #default="scope">-->
<!--          <el-button size="mini" @click="Edit(scope.$index,scope.row)">编辑</el-button>-->
<!--          <el-popconfirm title="是否确认删除？">-->
<!--            <template #reference>-->
<!--              <el-button size="mini" type="danger" @click="Delete(scope.$index,scope.row)">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
<!--        </template>-->
<!--      </Column>-->
<!--    </TreeTable>-->
    <div style="margin:15px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="paginations.currentPage"
          :page-sizes="paginations.pageSizes"
          :page-size="paginations.PageSize"
          layout="total,sizes,prev,pager,next,jumper"
          :total="tableData.length">
      </el-pagination>
    </div>
  </div>
<!--      编辑-->
      <el-dialog title="更改学生信息" v-model="dialogVisible1" width="30%" >
        <el-form :model="form" label-width="70px">
          <el-form-item label="ID" >
            <el-input v-model="form.userid" autocomplete="off" style="width:80%" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="用户名" >
            <el-input v-model="form.username" autocomplete="off" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio v-model="form.gender" label="男">男</el-radio>
            <el-radio v-model="form.gender" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="学校" >
            <el-input v-model="form.school" autocomplete="off" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="年级" >
            <el-input v-model="form.grade" autocomplete="off" style="width:80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="dialogUpdateConfirm">确 定</el-button>
        </span>
        </template>
      </el-dialog>
      
<!--      新增-->
        <el-dialog title="提示" v-model="dialogVisible" width="30%" >
          <el-form :model="form" label-width="70px">
            <el-form-item label="ID">
              <el-input v-model="form.userid" style="width:80%"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="form.username" style="width:80%"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio v-model="form.gender" label="男">男</el-radio>
              <el-radio v-model="form.gender" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="学校">
              <el-input v-model="form.school" style="width:80%"></el-input>
            </el-form-item>
            <el-form-item label="年级">
              <el-input v-model="form.grade" style="width:80%"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible=false">取消</el-button>
              <el-button type="primary" @click="save">确认</el-button>
            </span>
          </template>
        </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Admin",
  data(){
    return{
      form:{},
      dialogVisible:false,//控制新增弹出框的显示与否
      dialogVisible1: false,//控制更新弹出框的显示与否
      search:'',
      paginations:{
        // 默认显示第几页
        currentPage:1,
        // 个数选择器（可修改）
        pageSizes:[1,2,3,4],
        // 默认每页显示的条数（可修改）
        PageSize:4,
      },
      tableData:[],
      tableDataAnother:[],
    }
  },
  watch: {
    search(newVal) {
      // console.log(newVal);
      this.tableDataAnother=this.tableData
      // const fuzzy = this.search;
      if(newVal){
        this.tableDataAnother = this.tableDataAnother.filter((item) => {
          var reg = new RegExp(newVal, "gi");
          return reg.test(item.username)
        });
        this.tableData = this.tableDataAnother;
      }
      else{
        this.load();
      }
    }
  },
  created() {
    this.load()
    // this.tableDataAnother=this.tableData
  },
  methods:{
    load(){
      // 发送axios请求  $http是封装的接口地址
      request.get("/user/userad").then(res=>{
        // 把用户信息赋值给tableData
        console.log(res)
        this.tableData=res;
      })
    },
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    // 编辑
    save(){
        request.post("/user/userad", this.form).then(res => {
          if(res){
            this.$message.success("编辑成功")
            this.load()
          }else{
            this.$message.error("编辑失败")
          }
        })
      this.dialogVisible=false
    },
    Edit(row){
      console.log(row.userid);//获取当前编辑学生的学号
      this.dialogVisible1= true,
          this.form.userid=row.userid,
          this.form.username=row.username,
          this.form.gender=row.gender,
          this.form.school=row.school,
          this.form.grade=row.grade
    },
    Delete(userid){
      // console.log(userid)
      request.delete("/user/userad/" + userid).then(
          res=>{
            if(res){
              this.$message.success("删除成功")
              this.load()
            }else{
              this.$message.error("删除失败")
            }
          }
      )
    },
    // 编辑
    dialogUpdateConfirm(){
      request.put("/user/userad", this.form).then(res=>{
        console.log(res)
        this.load()
      })
      this.dialogVisible1=false
    },
    handleSizeChange(val){
      this.paginations.PageSize=val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.paginations.currentPage=1
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val){
      this.paginations.currentPage=val
      console.log(`当前页: ${val}`);
    },
    exp(){
      window.open("/api/userl/export")
    },
    ImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },
  },
}
</script>
<script setup>
// import { ArrowDown } from '@element-plus/icons-vue'
</script>
<style scoped>
/*html, body {*/
/*  margin: 0;*/
/*  padding: 0;*/
/*  height: 100%;*/
/*  background: #ebecee;*/
/*  position:fixed;*/
/*}*/
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>