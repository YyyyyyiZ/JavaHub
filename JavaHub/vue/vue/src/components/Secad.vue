<template>
  <div>
    <!--      功能区域-->
    <div style="margin: 15px;">
      <el-button @click="add">新增</el-button>
      <el-upload action="/api/section/import" :show-file-list=false accept="xlsx" :on-success="ImportSuccess" style="display:inline-block">
        <el-button class="ml-5">导入</el-button>
      </el-upload>
      <el-button @click="exp" class="ml-5">导出</el-button>
    </div>
    <!--      搜索区域-->
    <div style="margin: 15px">
      <el-input v-model="search" placeholder="请输入内容" style="width:25%" clearable></el-input>
    </div>
    <div>
      <el-table :data="tableData.slice((paginations.currentPage-1)*paginations.PageSize,paginations.currentPage*paginations.PageSize)" border stripe style="width: 100vw ;margin:10px">
        <el-table-column prop="secid" label="secid" sortable />
        <el-table-column prop="secname" label="secname"  />
        <el-table-column prop="chapid" label="chapid"  />
<!--        <el-table-column prop="knowdetail" label="knowdetail"  />-->
        <!--        <el-table-column prop="grade" label="grade" />-->
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="mini" @click="Edit(scope.row)">编辑</el-button>
            <el-popconfirm title="是否确认删除？" @confirm="Delete(scope.row.secid)">
              <template #reference>
                <el-button size="mini" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
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
        <el-form-item label="章节ID" >
          <el-input v-model="form.secid" autocomplete="off" style="width:80%" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="章节名" >
          <el-input v-model="form.secname" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="模块ID" >
          <el-input v-model="form.chapid" autocomplete="off" style="width:80%"></el-input>
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
        <el-form-item label="章节ID">
          <el-input v-model="form.secid" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="章节名">
          <el-input v-model="form.secname" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="模块ID">
          <el-input v-model="form.chapid" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible=false">取消</el-button>
              <el-button type="primary" @click="save">确认</el-button>
            </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Secad",
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
        pageSizes:[5,10,15],
        // 默认每页显示的条数（可修改）
        PageSize:5,
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
          return reg.test(item.secname)
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
  },
  methods:{
    load(){
      // 发送axios请求  $http是封装的接口地址
      request.get("/section/secad").then(res=>{
        // 把用户信息赋值给tableData
        console.log(res)
        this.tableData=res;
      })
    },
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    save(){
      request.post("/section/secad", this.form).then(res => {
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
      console.log(row.secid);//获取当前编辑学生的学号
      this.dialogVisible1= true,
          this.form.secid=row.secid,
          this.form.secname=row.secname,
          this.form.chapid=row.chapid
    },
    Delete(secid){
      // console.log(secid)
      request.delete("/section/secad/" + secid).then(
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
    dialogUpdateConfirm(){
      request.put("/section/secad", this.form).then(res=>{
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
      window.open("/api/section/export")
    },
    ImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },
  },
}
</script>

<style scoped>

</style>