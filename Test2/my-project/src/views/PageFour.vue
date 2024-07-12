<template>
    <div class="chat-page">
      <el-row>
        <el-col :span="6">
            <!--选择社团-->
          <el-select v-model="selectedClub" placeholder="社团名称" @change="loadMessages">   
            <el-option  
              v-for="club in clubs"
              :key="club.id"
              :label="club.name"
              :value="club.id">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" class="chat-container">
          <div class="messages" ref="messagesContainer">
            <div v-for="(message, index) in messages" :key="index" :class="['message', { 'sent': message.sent }]">
              {{ message.text }}
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
            <!--用于输入消息-->
          <el-input
            type="textarea"
            v-model="newMessage"
            @keyup.enter.native="sendMessage"
            placeholder="输入消息">
          </el-input>
          <el-button type="primary" @click="sendMessage">发送</el-button>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { ElMessage } from 'element-plus';
  
  export default {
    name: 'PageFour',
    setup() {
      const selectedClub = ref(null);
      const clubs = ref([
        { id: 1, name: '社团一' },
        { id: 2, name: '社团二' },
        { id: 3, name: '社团三' },
      ]);
  
      const messages = ref([]);
      const newMessage = ref('');
  
      const loadMessages = (clubId) => {
        // 模拟从服务器加载消息
        messages.value = [
          { text: '欢迎来到' + clubs.value.find(club => club.id === clubId).name, sent: false },
          { text: '请在这里分享你的想法。', sent: false },
        ];
      };
  
      const sendMessage = () => {
        if (!newMessage.value.trim()) {
          ElMessage.error('消息不能为空');
          return;
        }
        messages.value.push({ text: newMessage.value, sent: true });
        newMessage.value = '';
  
        // 模拟接收回复消息
        setTimeout(() => {
          messages.value.push({ text: '收到' + newMessage.value, sent: false });
          scrollToBottom();
        }, 1000);
      };
  
      const scrollToBottom = () => {
        const container = document.querySelector('.messages');
        container.scrollTop = container.scrollHeight;
      };
  
      onMounted(() => {
        if (selectedClub.value) {
          loadMessages(selectedClub.value);
        }
      });
  
      return {
        selectedClub,
        clubs,
        messages,
        newMessage,
        sendMessage,
      };
    },
  };
  </script>
  
  <style scoped>
  .chat-page {
    padding: 20px;
    
  }
  
  .chat-container {
    margin-top: 20px;
    height: 300px;
    overflow-y: auto;
    border: 1px solid #ddd;
    padding: 10px;
  }
  
  .messages {
    display: flex;
    flex-direction: column;
  }
  
  .message {
    background-color: #e1f5fe;
    border-radius: 10px;
    padding: 10px;
    margin: 5px 0;
    align-self: flex-start;
    max-width: 60%;
  }
  
  .message.sent { 
    background-color: #c8e6c9;
    align-self: flex-end;
  }
  </style>
