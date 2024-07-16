<template>
  <div class="chat-page">
    <el-row>
      <el-col :span="6">
        <!--选择社团-->
        <el-select v-model="selectedClub" placeholder="社团名称" @change="loadMessages">
          <el-option label="所有" value="all"></el-option>
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
    const clubs = ref([]);
    const messages = ref([]);
    const newMessage = ref('');
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');

    const loadMessages = async (clubId) => {
      // 清空旧消息
      messages.value = [];
      try {
        const response = await fetch(`http://localhost:8088/api/messages?clubId=${clubId}`);
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();
        console.log('Loaded messages:', data); // Debug log
        messages.value = data.map(msg => ({
          text: msg.message,
          username: msg.username,
          sent: msg.userId === userId // 通过比较userId来设置sent属性
        }));
        console.log('Processed messages:', messages.value); // Debug log
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
        console.log('Sent message:', {
          message: newMessage.value,
          userId: userId,
          username: username,
          clubId: selectedClub.value,
          timestamp: new Date().toISOString()
        }); // Debug log
        messages.value.push({ text: newMessage.value, username: username, sent: true });
        newMessage.value = '';
        scrollToBottom();
      } catch (error) {
        ElMessage.error('发送消息失败');
        console.error('Failed to send message:', error);
      }
    };

    const scrollToBottom = () => {
      const container = document.querySelector('.messages');
      container.scrollTop = container.scrollHeight;
    };

    const fetchClubs = async (userId) => {
      try {
        const response = await fetch(`http://localhost:8088/api/clubs/user/${userId}`);
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();
        clubs.value = [{ id: 'all', name: '所有' }, ...data];
      } catch (error) {
        console.error('获取社团数据失败', error);
      }
    };

    onMounted(() => {
      fetchClubs(userId);
    });

    return {
      selectedClub,
      clubs,
      messages,
      newMessage,
      sendMessage,
      loadMessages,
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
</style>
