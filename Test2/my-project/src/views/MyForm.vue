<template>
  <el-dialog v-model="dialogVisible" class="customer-container" :title="title" width="30%" @close="dialogClose">
    <el-alert style="margin-bottom: 10px" :show-icon="options.showIcon" :title="text" :type="options.type" :closable="false">
    </el-alert>
    <el-form label-width="100px" ref="formRef" :model="formData" :rules="formRules">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="formData.name" />
      </el-form-item>
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="formData.studentId" />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="formData.gender">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身高" prop="height">
        <el-input v-model="formData.height" />
      </el-form-item>
      <el-form-item label="体重" prop="weight">
        <el-input v-model="formData.weight" />
      </el-form-item>
      <el-form-item label="年龄" prop="birthday">
        <el-input v-model="formData.birthday" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, defineProps, defineExpose, defineEmits } from 'vue';
import { ElButton, ElDialog, ElForm, ElFormItem, ElInput, ElAlert, ElRadio, ElRadioGroup } from 'element-plus';

// 定义props
const props = defineProps({
  title: {
    type: String,
    default: '编辑资料',
  },
  text: {
    type: String,
    default: '请输入以下资料',
  },
  options: {
    type: Object,
    default: () => ({
      type: 'success',
      showIcon: true,
    }),
  },
});

// 定义emits
const emit = defineEmits(['submit', 'closed']);

// 定义表单数据
let dialogVisible = ref(false);
let formData = ref({
  name: '',
  studentId: '',
  gender: '',
  height: '',
  weight: '',
  birthday: '',
});
let formRef = ref();
let formRules = ref({
  name: [
    {
      required: true,
      message: '请输入姓名',
      trigger: 'blur',
    },
  ],
  studentId: [
    {
      required: true,
      message: '请输入学号',
      trigger: 'blur',
    },
  ],
  gender: [
    {
      required: true,
      message: '请输入性别',
      trigger: 'blur',
    },
  ],
  height: [
    {
      required: true,
      message: '请输入身高',
      trigger: 'blur',
    },
  ],
  weight: [
    {
      required: true,
      message: '请输入体重',
      trigger: 'blur',
    },
  ],
  birthday: [
    {
      required: true,
      message: '请输入年龄',
      trigger: 'blur',
    },
  ],
});

// 重置表单
function resetForm() {
  formData.value = {
    name: '',
    studentId: '',
    gender: '',
    height: '',
    weight: '',
    birthday: '',
  };
}

// 提交数据
function submitForm() {
  formRef.value.validate((valid, fields) => {
    if (valid) {
      dialogVisible.value = false;
      emit('submit', JSON.parse(JSON.stringify(formData.value)));
    }
  });
}

// 关闭弹窗
function dialogClose() {
  emit('closed');
}

defineExpose({
  showDialog: () => {
    resetForm();
    dialogVisible.value = true;
  },
  hideDialog: () => {
    dialogVisible.value = false;
  },
  formData,
});
</script>

<style>
/* 由于直接添加到body所以没有使用scoped */
.customer-container .el-dialog__body {
  padding-top: 0;
  padding-bottom: 0;
}
</style>
