<template>
  <div class="homepage">
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.src" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>

    <div class="activities">
      <h2>社团活动</h2>
      <div class="activity-container" v-for="(activity, index) in sortedActivities" :key="index">
        <div class="activity-details">
          <h3 class="club-name">{{ activity.clubName }}</h3>
          <p class="activity-name">活动名称：{{ activity.name }}</p>
          <p class="activity-description">详情：{{ activity.description }}</p>
          <p class="activity-location">地点：{{ activity.location }}</p>
          <p class="activity-date">{{ formatDate(activity.publishTime) }}</p>
          <div v-if="activity.images.length">
            <img :src="activity.images[0]" class="activity-image" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
import { format, isValid } from 'date-fns';

export default {
  name: 'Home',
  setup() {
    const activities = ref([]);
    const carouselItems = ref([
  { src: '/src/assets/fengjing1.jpg' },
  { src: '/src/assets/fengjing2.jpg' },
  { src: '/src/assets/fengjing3.jpg' }
]);


    const fetchActivities = async () => {
      try {
        const response = await axios.get('http://localhost:8088/api/activities/approved');
        activities.value = response.data;
      } catch (error) {
        console.error('Error fetching activities:', error);
      }
    };

    onMounted(() => {
      fetchActivities();
    });

    const sortedActivities = computed(() => {
      return activities.value.sort((a, b) => new Date(b.publishTime) - new Date(a.publishTime));
    });

    const formatDate = (dateString) => {
      const date = new Date(dateString);
      return isValid(date) ? format(date, 'yyyy-MM-dd HH:mm:ss') : 'Invalid Date';
    };

    return {
      activities,
      sortedActivities,
      formatDate,
      carouselItems
    };
  }
};
</script>

<style scoped>
.homepage {
  padding: 20px;
}

.carousel-image {
  width: 100%; /* 固定宽度 */
  height: 400px; /* 固定高度 */
  object-fit: cover; /* 保持比例并覆盖整个区域 */
  object-position: center; /* 将图片居中 */
}

.activities {
  margin-top: 20px;
}

.activity-container {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 20px;
}

.activity-details {
  flex: 1;
  margin-right: 20px;
}

.club-name {
  font-size: 20px;
  font-weight: bold;
}

.activity-name,
.activity-description,
.activity-loaction,
.activity-location {
  margin: 5px 0;
}

.activity-date {
  font-size: 12px;
  color: #909399;
  margin: 5px 0;
}

.activity-image {
  width: 200px;
  margin-top: 10px;
}
</style>
