<template>
  <Toast position="top-center"/>
  <div>
    <DataTable :value="products" responsiveLayout="scroll" v-model:expandedRows="expandedRows"
               v-model:filters="filters" filterDisplay="menu" :rowHover="true"
               :globalFilterFields="['wrongid','exerques','exertype','knowpoint','remarks']"
               editMode="cell" @cell-edit-complete="onCellEditComplete" class="editable-cells-table">
      <template #header>
        <div class="flex align-items-center">
          <span class="p-input-icon-left">
            <i class="pi pi-search" />
            <InputText v-model="filters['global'].value" placeholder="搜索错题集" />
          </span>
        </div>
      </template>
      <Column field="expander" style="min-width:20px" :expander="true">
      </Column>
      <Column field="wrongid" style="max-width:0px" hidden="true">
        <template #body="slotProps">
          {{slotProps.data.wrongid}}
        </template>
      </Column>
      <Column field="exerques" header="题目" style="min-width:400px">
        <template #body="slotProps">
          {{slotProps.data.exerques}}
        </template>
      </Column>
      <Column field="exertype" header="题型" style="min-width:150px">
        <template #body="slotProps">
          {{slotProps.data.exertype}}
        </template>
      </Column>
      <Column field="knowpoint" header="知识点" style="min-width:200px">
        <template #body="slotProps">
          {{slotProps.data.knowpoint}}
        </template>
      </Column>
      <Column field="remarks" header="备注" style="min-width:200px" >
        <template #editor="{ data, field }">
          <InputText v-model="data[field]" autofocus />
        </template>
      </Column>
      <Column header="删除">
        <template #body="slotProps">
          <Button @click="confirmDeleteProduct(slotProps.data)" icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" />
        </template>
      </Column>

      <template #expansion="slotProps">
        <div v-if="slotProps.data.exertype==='单选题'">
          <SingleCh :data="slotProps.data"></SingleCh>
          <p>参考答案：{{slotProps.data.exerans}}</p>
        </div>
        <div v-if="slotProps.data.exertype==='多选题'">
          <MultiCh :data="slotProps.data"></MultiCh>
          <p>参考答案：{{slotProps.data.answer}}</p>
        </div>
        <div v-if="slotProps.data.exertype==='判断题'">
          <Judge :data="slotProps.data"></Judge>
          <p>参考答案：{{slotProps.data.exerans}}</p>
        </div>
        <div v-if="slotProps.data.exertype==='填空题'">
          <Fill :data="slotProps.data"></Fill>
          <p>参考答案：{{slotProps.data.exerans}}</p>
        </div>
      </template>

      <template #footer>
        总共有{{products ? products.length : 0 }} 道题.
      </template>
    </DataTable>

    <Dialog v-model:visible="deleteProductDialog" :style="{width: '450px'}" header="确认删除" :modal="true">
      <div class="flex align-items-center justify-content-center">
        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
        <span v-if="product">确定要从错题集中删除这道题目吗？</span>
      </div>
      <template #footer>
        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
      </template>
    </Dialog>
  </div>
</template>

<script>
import SingleCh from "@/pattern/SingleCh";
import MultiCh from "@/pattern/MultiCh";
import Judge from "@/pattern/Judge";
import Fill from "@/pattern/Fill";
import {FilterMatchMode,FilterOperator} from 'primevue/api';
import router from "@/router";

export default {
  components:{
    'SingleCh':SingleCh,
    'MultiCh':MultiCh,
    'Judge':Judge,
    'Fill':Fill,
  },
  data() {
    return {
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      expandedRows: [],
      productDialog: false,
      deleteProductDialog: false,
      product: {},
      products: {},
      submitted: false,
      filters: {
        'global': {value: null, matchMode: FilterMatchMode.CONTAINS},
        'name': {operator: FilterOperator.AND, constraints: [{value: null, matchMode: FilterMatchMode.STARTS_WITH}]},
        'description': {operator: FilterOperator.AND, constraints: [{value: null, matchMode: FilterMatchMode.STARTS_WITH}]},
        'price': {value: null, matchMode: FilterMatchMode.IN},
      },
    }
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
  },
  mounted() {
    this.request.post("/wrongset/select",this.user).then(res => {
      this.products=res
      console.log(res)
    })
  },
  methods: {
    onCellEditComplete(event) {
      let { data, newValue, field } = event;
      if (newValue.trim().length > 0)
      {
        data[field] = newValue;
        let obj=JSON.stringify({
          "wrongid":data.wrongid,
          "remarks":newValue
        })
        this.request.post("/wrongset/update",obj).then(res => {
          if(res){
            this.$toast.add({severity:'success', summary: 'Success', detail:'修改备注成功', life: 3000});
          }else{
            this.$toast.add({severity:'error', summary: 'Error', detail:'修改备注失败', life: 3000});
          }
        })
        this.product = {};
      }
      else
        event.preventDefault();
      },
    confirmDeleteProduct(product) {
      this.product = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.products = this.products.filter(val => val.wrongid !== this.product.wrongid);
      let obj=JSON.stringify({
        "wrongid":this.product.wrongid
      })
      this.request.post("/wrongset/delete",obj).then(res => {
        if(res){
          this.$toast.add({severity:'success', summary: 'Success', detail:'删除成功', life: 3000});
        }else{
          this.$toast.add({severity:'error', summary: 'Error', detail:'删除失败', life: 3000});
        }
      })
      this.deleteProductDialog = false;
      this.product = {};
    },

  }
}
</script>

<style lang="scss" scoped>
.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
::v-deep(.editable-cells-table td.p-cell-editing) {
  padding-top: 0;
  padding-bottom: 0;
}
</style>