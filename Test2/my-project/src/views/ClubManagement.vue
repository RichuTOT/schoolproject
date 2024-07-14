<template>
  <el-container style="height: 100vh;">
    <el-aside width="200px" style="background-color: #f2f2f2;">
      <!-- 侧边栏内容 -->
      <el-menu default-active="all" class="el-menu-vertical-demo" @select="handleCategorySelect">
        <el-menu-item index="all">全部</el-menu-item>
        <el-menu-item index="文学类">文学类</el-menu-item>
        <el-menu-item index="体育类">体育类</el-menu-item>
        <el-menu-item index="哲学类">哲学类</el-menu-item>
        <el-menu-item index="其他">其他</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background-color: #b3c0d1; display: flex; align-items: center;">
        <!-- 头部内容 -->
        <el-input
          v-model="searchQuery"
          placeholder="输入搜索内容"
          prefix-icon="el-icon-search"
          @keyup.enter="handleSearch"
          style="margin-right: 10px; flex: 1;"
        />
        <el-button type="primary" @click="handleSearch">点击搜索</el-button>
      </el-header>
      <el-main>
        <!-- 主内容 -->
        <el-table :data="paginatedClubs" class="club-table" style="margin-top: 20px;">
          <el-table-column prop="index" label="序号" width="100">
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="社团名称" width="200"></el-table-column>
          <el-table-column prop="date" label="成立时间" width="180">
            <template v-slot="scope">
              <span>{{ formatDate(scope.row.date) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="president" label="社长" width="120"></el-table-column>
          <el-table-column label="操作" width="150">
            <template v-slot="scope">
              <el-button type="danger" size="mini" @click="deleteClub(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-if="!filteredClubs.length" class="no-data">
          无合适社团
        </div>
        <el-pagination
          v-if="filteredClubs.length"
          style="margin-top: 20px;"
          background
          layout="prev, pager, next"
          :total="filteredClubs.length"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { format } from 'date-fns';

export default {
  name: 'ClubManagement',
  data() {
    return {
      searchQuery: '',
      clubs: [],
      currentPage: 1,
      pageSize: 10, // 每页显示的条数
      selectedCategory: 'all',
      userId: localStorage.getItem('userId'), // assuming you store userId in localStorage
    };
  },
  computed: {
    filteredClubs() {
      return this.clubs.filter(club => {
        const searchRegex = new RegExp(this.searchQuery, 'i');
        const matchesSearchQuery = (
          searchRegex.test(club.name) ||
          searchRegex.test(club.date) ||
          searchRegex.test(club.president) // 确保使用正确字段
        );
        const matchesCategory = (
          this.selectedCategory === 'all' || club.category === this.selectedCategory
        );
        return matchesSearchQuery && matchesCategory;
      });
    },
    paginatedClubs() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredClubs.slice(start, end);
    },
  },
  methods: {
    async fetchClubs() {
      try {
        const response = await axios.get('http://localhost:8088/api/clubs', { withCredentials: true });
        this.clubs = response.data;
      } catch (error) {
        console.error('获取数据失败', error);
      }
    },
    handleSearch() {
      this.currentPage = 1; // 重置分页到第一页
    },
    async deleteClub(id) {
      try {
        await axios.delete(`http://localhost:8088/api/clubs/${id}`, { withCredentials: true });
        this.fetchClubs();
        ElMessage.success('删除成功');
      } catch (error) {
        console.error('删除失败', error);
        ElMessage.error('删除失败');
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    handleCategorySelect(key) {
      this.selectedCategory = key;
      this.currentPage = 1; // 重置分页到第一页
    },
    formatDate(dateString) {
      return format(new Date(dateString), 'yyyy-MM-dd');
    }
  },
  async mounted() {
    await this.fetchClubs();
  },
};
</script>

<style scoped>
.el-container {
  height: 100vh;
}

.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.el-main {
  height: calc(100vh - 60px); /* 减去header的高度 */
  overflow-y: auto;
}

.el-table th, .el-table td {
  text-align: center;
}

.no-data {
  text-align: center;
  margin-top: 20px;
  color: #909399;
}

.el-pagination {
  text-align: center;
}
</style>
