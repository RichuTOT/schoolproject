import { reactive } from 'vue';

export const store = reactive({
  favoriteClubs: [],
  applicationRequests: [],

  addFavorite(club) {
    if (!this.isFavorite(club)) {
      this.favoriteClubs.push({
        ...club,
        date: new Date().toISOString().slice(0, 10),
      });
    }
  },

  removeFavorite(club) {
    this.favoriteClubs = this.favoriteClubs.filter(c => c.name !== club.name);
  },

  isFavorite(club) {
    return this.favoriteClubs.some(c => c.name === club.name);
  },

  addApplicationRequest(club) {
    if (!this.applicationRequests.some(req => req.name === club.name)) {
      this.applicationRequests.push({
        ...club,
        status: '审批中',
        date: new Date().toISOString().slice(0, 10),
      });
    }
  },

  updateApplicationRequest(club, status) {
    const request = this.applicationRequests.find(req => req.name === club.name);
    if (request) {
      request.status = status;
    }
  },

  getApplicationRequests() {
    return this.applicationRequests;
  }
});
