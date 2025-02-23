document.addEventListener('DOMContentLoaded', () => {  
  const nav = document.querySelector(".nav");
  const searchIcon = document.querySelector("#searchIcon");
  const navOpenBtn = document.querySelector(".navOpenBtn");
  const navCloseBtn = document.querySelector(".navCloseBtn");
  const searchInput = document.getElementById("searchInput");
  const searchResults = document.getElementById("searchResults");

  if (searchIcon) { 
      searchIcon.addEventListener("click", () => {
          nav.classList.toggle("openSearch");
          nav.classList.remove("openNav");
          searchIcon.classList.toggle("uil-times");
          searchIcon.classList.toggle("uil-search");
      });
  }

  if (navOpenBtn) { 
      navOpenBtn.addEventListener("click", () => {
          nav.classList.add("openNav");
          nav.classList.remove("openSearch");
          searchIcon.classList.replace("uil-times", "uil-search");
      });
  }

  if (navCloseBtn) { 
      navCloseBtn.addEventListener("click", () => {
          nav.classList.remove("openNav");
      });
  }

  if (searchInput) { 
      searchInput.addEventListener("input", async () => {


          if (window.scrollY > 0) {
              window.scrollTo({
                  top: 0,
                  behavior: 'smooth'
              });
          }


          const searchTerm = searchInput.value.toLowerCase();
          searchResults.innerHTML = "";

          if (searchTerm.length > 0) {
              const results = await getSearchResults(searchTerm);

              if (results.length > 0) {
                  results.forEach(result => {
                      const resultCard = document.createElement("a");
                      resultCard.href = result.link;
                      resultCard.classList.add("search-card");
                      var imageSrc = `/images/${result.image}`;
                      console.dir(imageSrc);
                      resultCard.innerHTML = `
                          <img src="${imageSrc}" alt="${result.title}">
                          <div class="search-info">
                              <h3>${result.title}</h3>
                              <p>${result.description}</p>
                          </div>
                      `;

                      searchResults.appendChild(resultCard);
                  });
                  searchResults.classList.add("show");
              } else {
                  const noResults = document.createElement("p");
                  noResults.textContent = "No results found.";
                  searchResults.appendChild(noResults);
                  searchResults.classList.add("show");
              }
          } else {
              searchResults.classList.remove("show");
          }
      });
  }


  //  search request await async

  async function getSearchResults(searchTerm) {

      try{
          let response = await fetch(`/search/course?query=${encodeURIComponent(searchTerm)}`)
          if(!response.ok){
              throw new Error("Failed to fetch courses");
          }

          return await response.json();

      } catch (Error){
          console.log(Error);
          return []
      }
  }

  document.addEventListener("click", (event) => {
      if (!event.target.closest('.search-box')) {
          searchResults.classList.remove("show");
      }
  });












  const initSlider = () => {
    const imageList = document.querySelector(".slider-wrapper .image-list");
    const slideButtons = document.querySelectorAll(".slider-wrapper .slide-button");
    const sliderScrollbar = document.querySelector(".containerCourse .slider-scrollbar");
    const scrollbarThumb = sliderScrollbar.querySelector(".scrollbar-thumb");
    const maxScrollLeft = imageList.scrollWidth - imageList.clientWidth;
    
    // Handle scrollbar thumb drag
    scrollbarThumb.addEventListener("mousedown", (e) => {
        const startX = e.clientX;
        const thumbPosition = scrollbarThumb.offsetLeft;
        const maxThumbPosition = sliderScrollbar.getBoundingClientRect().width - scrollbarThumb.offsetWidth;
        
        // Update thumb position on mouse move
        const handleMouseMove = (e) => {
            const deltaX = e.clientX - startX;
            const newThumbPosition = thumbPosition + deltaX;
            // Ensure the scrollbar thumb stays within bounds
            const boundedPosition = Math.max(0, Math.min(maxThumbPosition, newThumbPosition));
            const scrollPosition = (boundedPosition / maxThumbPosition) * maxScrollLeft;
            
            scrollbarThumb.style.left = `${boundedPosition}px`;
            imageList.scrollLeft = scrollPosition;
        }
        // Remove event listeners on mouse up
        const handleMouseUp = () => {
            document.removeEventListener("mousemove", handleMouseMove);
            document.removeEventListener("mouseup", handleMouseUp);
        }
        // Add event listeners for drag interaction
        document.addEventListener("mousemove", handleMouseMove);
        document.addEventListener("mouseup", handleMouseUp);
    });
    // Slide images according to the slide button clicks
    slideButtons.forEach(button => {
        button.addEventListener("click", () => {
            const direction = button.id === "prev-slide" ? -1 : 1;
            const scrollAmount = imageList.clientWidth * direction;
            imageList.scrollBy({ left: scrollAmount, behavior: "smooth" });
        });
    });
     // Show or hide slide buttons based on scroll position
    const handleSlideButtons = () => {
        slideButtons[0].style.display = imageList.scrollLeft <= 0 ? "none" : "flex";
        slideButtons[1].style.display = imageList.scrollLeft >= maxScrollLeft ? "none" : "flex";
    }
    // Update scrollbar thumb position based on image scroll
    const updateScrollThumbPosition = () => {
        const scrollPosition = imageList.scrollLeft;
        const thumbPosition = (scrollPosition / maxScrollLeft) * (sliderScrollbar.clientWidth - scrollbarThumb.offsetWidth);
        scrollbarThumb.style.left = `${thumbPosition}px`;
    }
    // Call these two functions when image list scrolls
    imageList.addEventListener("scroll", () => {
        updateScrollThumbPosition();
        handleSlideButtons();
    });
}
window.addEventListener("resize", initSlider);
window.addEventListener("load", initSlider);
  



});













// script.js


document.addEventListener('DOMContentLoaded', () => {



    const coursesContainer = document.querySelector('.courses');
    const filterButtons = document.querySelectorAll('.filter');


    function displayCourses(category = 'all') {
        coursesContainer.innerHTML = ''; // Clear previous courses

        const filteredCourses = category === 'all' ? coursesDataLatest : coursesDataLatest.
        filter(course => course.category === category);

        filteredCourses.forEach(course => {
            const courseDiv = document.createElement('div');
            courseDiv.classList.add('course');
            courseDiv.innerHTML = `
                <img src="/images/${course.image}" alt="${course.title}">
                <div class="course-level">${course.level}</div>
                <h3 class="course-title">${course.title}</h3>
                <p class="course-description">${course.description}</p>
                <div class="course-rating">
                    ${'<i class="bx bxs-star"></i>'.repeat(course.rating)}
                </div>
                <div class="course-info">
                    <span>${course.duration}</span>
                    <span>${course.lectures}</span>
                </div>
                <div class="course-footer">
                    <i class="bx bx-heart"></i>
                </div>
            `;
            coursesContainer.appendChild(courseDiv);
        });
    }

    displayCourses(); // Display all courses initially

    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            filterButtons.forEach(btn => btn.classList.remove('active')); // Remove active class from all buttons
            button.classList.add('active'); // Add active class to clicked button
            const category = button.dataset.category;
            displayCourses(category);
        });
    });
});





