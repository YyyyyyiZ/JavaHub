<template>
  <div>
    <!--      功能区域-->
    <div style="margin: 15px;">
      <el-button @click="add">新增</el-button>
      <el-upload action="/api/knowledge/import" :show-file-list=false accept="xlsx" :on-success="ImportSuccess" style="display:inline-block">
        <el-button class="ml-5">导入</el-button>
      </el-upload>
      <el-button @click="exp" class="ml-5">导出</el-button>
    </div>
    <!--      搜索区域-->
    <div style="margin: 15px">
      <el-input v-model="search" placeholder="请输入内容" style="width:25%" clearable></el-input>
    </div>
    <div>
      <el-table :data="tableData.slice((paginations.currentPage-1)*paginations.PageSize,paginations.currentPage*paginations.PageSize)"
                :row-style="{height:'50px'}"
                :cell-style="{padding:'0px'}"
                border
                stripe style="width: 100vw ;margin:10px">
        <el-table-column prop="knowid" label="knowid" sortable />
        <el-table-column prop="secid" label="secid"  />
        <el-table-column prop="knowpoint" label="knowpoint"  />
        <el-table-column prop="knowdetail" label="knowdetail" show-overflow-tooltip/>

<!--        <el-table-column prop="grade" label="grade" />-->
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="mini" @click="Edit(scope.row)">编辑</el-button>
            <el-popconfirm title="是否确认删除？" @confirm="Delete(scope.row.knowid)">
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
        <el-form-item label="知识ID" >
          <el-input v-model="form.knowid" autocomplete="off" style="width:80%" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="章节ID" >
          <el-input v-model="form.secid" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="知识点" >
          <el-input v-model="form.knowpoint" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="详情" >
          <el-input v-model="form.knowdetail" autocomplete="off" style="width:80%"></el-input>
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
        <el-form-item label="知识ID">
          <el-input v-model="form.knowid" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="章节ID">
          <el-input v-model="form.secid" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="知识点">
          <el-input v-model="form.knowpoint" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="详情">
          <el-input v-model="form.knowdetail" style="width:80%"></el-input>
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
  name: "Knowad.vue",
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
          return reg.test(item.knowpoint)
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
      var arr=this
      request.get("/knowledge/knowad").then(res=>{
        // 把用户信息赋值给tableData
        console.log(res)
        arr.tableData=res;
      })
    },
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    save(){
      var arr=this
      request.post("/knowledge/knowad", arr.form).then(res => {
        if(res){
          arr.$message.success("编辑成功")
          this.load()
        }else{
          arr.$message.error("编辑失败")
        }
      })
      arr.dialogVisible=false
    },
    Edit(row){
      console.log(row.knowid);//获取当前编辑学生的学号
      this.dialogVisible1= true,
          this.form.knowid=row.knowid,
          this.form.secid=row.secid,
          this.form.knowpoint=row.knowpoint,
          this.form.knowdetail=row.knowdetail
    },
    Delete(knowid){
      // console.log(knowid)
      var arr=this
      request.delete("/knowledge/knowad/" + knowid).then(
          res=>{
            if(res){
              arr.$message.success("删除成功")
              this.load()
            }else{
              arr.$message.error("删除失败")
            }
          }
      )
    },
    dialogUpdateConfirm(){
      var arr=this
      request.put("/knowledge/knowad", arr.form).then(res=>{
        console.log(res)
        arr.load()
      })
      arr.dialogVisible1=false
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
      window.open("/api/knowledge/export")
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