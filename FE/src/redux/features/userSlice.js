import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  currentSongs: [],
  //dánh sách các bài hát hiện tại
  currentIndex: 0,
  //vị trí hiện tại trong danh sách
  loginStatus: true,
  //xem player đã active chưa
  isPlaying: false,
  // nhạc có đang chạy hay ko
  activeSong: {}, 
  // bài hát đang được active
  genreListId: '',
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setStatus: (state)=>{
      state.loginStatus= false;
    }
    // setActiveSong: (state, action) => {
    //   state.activeSong = action.payload.song;

    //   if (action.payload?.data?.tracks?.hits) {
    //     state.currentSongs = action.payload.data.tracks.hits;
    //   } else if (action.payload?.data?.properties) {
    //     state.currentSongs = action.payload?.data?.tracks;
    //   } else {
    //     state.currentSongs = action.payload.data;
    //   }

    //   state.currentIndex = action.payload.i;
    //   state.isActive = true;
    // },

    // nextSong: (state, action) => {
    //   if (state.currentSongs[action.payload]?.track) {
    //     state.activeSong = state.currentSongs[action.payload]?.track;
    //   } else {
    //     state.activeSong = state.currentSongs[action.payload];
    //   }

    //   state.currentIndex = action.payload;
    //   state.isActive = true;
    // },

    // prevSong: (state, action) => {
    //   if (state.currentSongs[action.payload]?.track) {
    //     state.activeSong = state.currentSongs[action.payload]?.track;
    //   } else {
    //     state.activeSong = state.currentSongs[action.payload];
    //   }

    //   state.currentIndex = action.payload;
    //   state.isActive = true;
    // },

    // playPause: (state, action) => {
    //   state.isPlaying = action.payload;
    // },

    // selectGenreListId: (state, action) => {
    //   state.genreListId = action.payload;
    // },
  },
});

export const { setStatus} = userSlice.actions;

export default userSlice.reducer;