<template>
  <div class="search-club">
    <div class="search-bar">
      <el-input
        v-model="searchQuery"
        placeholder="输入社团名称"
        prefix-icon="el-icon-search"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">点击搜索</el-button>
    </div>

    <el-table :data="filteredClubs" class="club-table" style="margin-top: 20px;">
      <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
      <el-table-column prop="date" label="发布时间" width="180"></el-table-column>
      <el-table-column prop="author" label="发布人" width="180"></el-table-column>
      <el-table-column prop="category" label="类别" width="180"></el-table-column>
      <el-table-column label="操作" width="100">
        <template v-slot="scope">
          <el-button type="text" @click="applyClub(scope.row)">申请</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="club-category" style="margin-top: 20px;">
      <h3>社团分类</h3>
      <el-row>
        <el-col :span="4">
          <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            @select="handleCategorySelect"
          >
            <el-menu-item index="1">学术类</el-menu-item>
            <el-menu-item index="2">文体类</el-menu-item>
            <el-menu-item index="3">志愿服务类</el-menu-item>
            <el-menu-item index="4">休闲类</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="20">
          <el-table :data="filteredByCategoryClubs" class="club-table">
            <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
            <el-table-column prop="date" label="发布时间" width="180"></el-table-column>
            <el-table-column prop="author" label="发布人" width="180"></el-table-column>
            <el-table-column prop="category" label="类别" width="180"></el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageTwo',
  data() {
    return {
      searchQuery: '',
      clubs: [
        { name: '社团A', date: '2024-07-05', author: '张三', category: '学术类' },
        { name: '社团B', date: '2024-07-05', author: '李四', category: '文体类' },
        { name: '社团C', date: '2024-07-05', author: '王五', category: '志愿服务类' },
        { name: '皇姓社团', date: '2024-07-05', author: '刘六', category: '休闲类' },
      ],
      selectedCategory: '',
    };
  },
  computed: {
    filteredClubs() {
      return this.clubs.filter(club => club.name.includes(this.searchQuery));
    },
    filteredByCategoryClubs() {
      return this.selectedCategory
        ? this.clubs.filter(club => club.category === this.selectedCategory)
        : this.clubs;
    },
  },
  methods: {
    handleSearch() {
      // 搜索逻辑
    },
    applyClub(club) {
      // 申请社团逻辑
      console.log(`申请加入: ${club.name}`);
    },
    handleCategorySelect(key, keyPath) {
      switch (key) {
        case '1':
          this.selectedCategory = '学术类';
          break;
        case '2':
          this.selectedCategory = '文体类';
          break;
        case '3':
          this.selectedCategory = '志愿服务类';
          break;
        case '4':
          this.selectedCategory = '休闲类';
          break;  
        default:
          this.selectedCategory = '';
          break;
      }
    },
  },
};
</script>

<style scoped>
.search-club {
  padding: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
}

.search-bar .el-input {
  flex: 1;
  margin-right: 10px;
}

.club-table {
  width: 100%;
}

.club-category {
  margin-top: 20px;
}

.el-menu-vertical-demo {
  border-right: none;
}

.el-table th, .el-table td {
  text-align: center;
}
</style>
