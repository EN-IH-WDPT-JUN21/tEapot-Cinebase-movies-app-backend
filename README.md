
<img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/tEapot-Cinebase-movies-app-backend/blob/main/logo-20.png" style = "max-width: 80px;">

<br />
<p align="center">

  <h3 align="center">CINEBASE</h3>

  <p align="center">
    Movie App project created by tEapot! team:
    <br />
    <a href="https://github.com/natyfromwonderland"><strong>Natalia Shilyaeva</strong></a>
    ·
    <a href="https://github.com/Mat-Poreda"><strong>Mateusz Poreda</strong></a>
    ·
    <a href="https://github.com/MigNeves"><strong>Miguel Neves</strong></a>
    ·
    <a href="https://github.com/patrykwieteska"><strong>Patryk Wieteska</strong></a>
  </p>
  
  
  Project Requirements
  ===========================
  
  The project will have at least these sections:
  - A section to search movies (the search should be case insensitive)
  - A movie detail
  - Login/Register section (only registered users can create playlists)
  - User profile page (if logged)
  - User playlist section (if logged)
  
  Project Architecture
  ===========================
  
  The project architecture was originally agreed to be the following, althought it evolved a bit following the evolving complexity:
  
  <img alt="project architecture" src="https://github.com/EN-IH-WDPT-JUN21/tEapot-Cinebase-movies-app-backend/blob/main/structure-Page-2.png">
  
  Database MySQL Set-up
  ===========================
  
  create database playlistApi;

  use playlistApi;

  CREATE USER 'ironhacker11'@'localhost' IDENTIFIED BY '1r0nH@ck3r';

  GRANT ALL PRIVILEGES ON \*.\* TO 'ironhacker11'@'localhost';

  FLUSH PRIVILEGES;
  
  Front End
  ===========================
  
  The front end part can be found in the following repo: https://github.com/EN-IH-WDPT-JUN21/tEapot-Cinebase-movies-app-frontend
  
  
  Look and Feel
  ===========================
  
  Bespoke UI elements:
  
  <img alt="project logo" src="https://github.com/EN-IH-WDPT-JUN21/tEapot-Cinebase-movies-app-backend/blob/main/iterface.png">
  
  Special Features
  ===========================
  
  You will need to create an accout to enjoy the enhanced functionality. Up to you if you want to verify your email or not. 
  
  You can select your profile picture by clicking on the default Groot and add info about yourself by clicking on 'edit bio'.
  
  Remarks
  ===========================
  When starting up playlist-service you may encounter JWT timeout error. In such case please start the application once again.
  
  This application is using IMDB API which is restricted in number of requets per day. 
  
  If you will be unable to fetch movies data you can replace the used key with a new one in frontend class src\app\service\movie-service\movies.service.ts

  
  
  Contributing
  ===========================
  
  Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are 
  **greatly appreciated**.

  1. Fork the Project
  2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
  3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
  4. Push to the Branch (`git push origin feature/AmazingFeature`)
  5. Open a Pull Request


  License
  ===========================

  Distributed under the MIT License. See `LICENSE` for more information


  Thank you!
  ===========================
  
  P.S. Try a route that doesn't exist, you might discover something new...
  
