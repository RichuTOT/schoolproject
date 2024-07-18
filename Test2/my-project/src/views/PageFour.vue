<template>
  <div class="chat-page">
    <el-row>
      <el-col :span="6">
        <!--选择社团-->
        <el-select v-model="selectedClub" placeholder="请选择社团" @change="loadMessages">
          <el-option label="请选择社团" value="" disabled></el-option>
          <el-option
            v-for="club in clubs"
            :key="club.id"
            :label="club.name"
            :value="club.id">
          </el-option>
          <el-option label="所有" value="9999"></el-option>
        </el-select>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" class="chat-container">
        <div class="messages" ref="messagesContainer">
          <div
            v-for="(message, index) in messages"
            :key="index"
            :class="['message', { 'sent': message.sent }]">
            <div class="message-username">{{ message.username }}</div>
            <div class="message-text">{{ message.text }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20">
        <!--用于输入消息-->
        <el-input
          type="textarea"
          v-model="newMessage"
          @keyup.enter.native="sendMessage"
          placeholder="输入消息">
        </el-input>
      </el-col>
      <el-col :span="4" class="send-button-col">
        <el-button type="primary" @click="sendMessage" class="send-button">发送</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { ElMessage } from 'element-plus';

export default {
  name: 'PageFour',
  setup() {
    const selectedClub = ref('');
    const clubs = ref([]);
    const messages = ref([]);
    const newMessage = ref('');
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');
    const messagesContainer = ref(null);

    const loadMessages = async (clubId) => {
      if (!clubId) return; // 如果没有选择社团，直接返回
      messages.value = []; // 清空旧消息
      try {
        const response = await fetch(`http://localhost:8088/api/messages?clubId=${clubId}`);
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();
        data.forEach(msg => {
          messages.value.push({
            text: msg.message,
            username: msg.username,
            sent: msg.userId.toString() === userId.toString() // 确保类型和值匹配
          });
        });
        scrollToBottom();
      } catch (error) {
        console.error('Failed to load messages:', error);
      }
    };

    const sendMessage = async () => {
      if (!newMessage.value.trim()) {
        ElMessage.error('消息不能为空');
        return;
      }
      if (!selectedClub.value) {
        ElMessage.error('请选择社团');
        return;
      }
      try {
        const response = await fetch('http://localhost:8088/api/messages', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            message: newMessage.value,
            userId: userId,
            username: username,
            clubId: selectedClub.value,
            timestamp: new Date().toISOString()
          }),
        });
        if (!response.ok) throw new Error('Network response was not ok');
        messages.value.push({ text: newMessage.value, username: username, sent: true });
        newMessage.value = '';
        scrollToBottom();
      } catch (error) {
        ElMessage.error('发送消息失败');
        console.error('Failed to send message:', error);
      }
    };

    const scrollToBottom = () => {
      const container = messagesContainer.value;
      container.scrollTop = container.scrollHeight;
    };

    const fetchClubs = async (userId) => {
      try {
        const response = await fetch(`http://localhost:8088/api/clubs/user/${userId}`);
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();
        clubs.value = data;
      } catch (error) {
        console.error('获取社团数据失败', error);
      }
    };

    onMounted(() => {
      fetchClubs(userId);
    });

    watch(selectedClub, (newClubId) => {
      if (newClubId) {
        loadMessages(newClubId);
      }
    });

    return {
      selectedClub,
      clubs,
      messages,
      newMessage,
      sendMessage,
      loadMessages,
      scrollToBottom,
      messagesContainer
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
  height: calc(100vh - 300px); /* 调整这里的高度来改变聊天框大小 */
  overflow-y: auto;
  border: 1px solid #ddd;
  padding: 10px;
}

.messages {
  display: flex;
  flex-direction: column;
}

.message {
  display: flex;
  flex-direction: column;
  max-width: 60%;
  margin: 5px 0;
  background-color: #e1f5fe;
  border-radius: 10px;
  padding: 10px;
  border: 2px solid #81d4fa;
  align-self: flex-start;
}

.message.sent {
  align-self: flex-end;
  background-color: #c8e6c9;
  border: 2px solid #a5d6a7;
}

.message-username {
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 12px;
  margin-left: 5px; /* 确保用户名不在消息边框内 */
}

.message-text {
  word-break: break-word;
}

.send-button-col {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}

.send-button {
  width: 100%;
}
</style>
