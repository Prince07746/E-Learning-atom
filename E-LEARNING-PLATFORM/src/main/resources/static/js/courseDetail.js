
document.addEventListener("DOMContentLoaded", () => {
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
      searchInput.addEventListener("input", () => {
        if (window.scrollY > 0) {
          window.scrollTo({
            top: 0,
            behavior: "smooth",
          });
        }

        const searchTerm = searchInput.value.toLowerCase();
        searchResults.innerHTML = "";

        if (searchTerm.length > 0) {
          const results = getSearchResults(searchTerm);

          if (results.length > 0) {
            results.forEach((result) => {
              const resultCard = document.createElement("a");
              resultCard.href = result.link;
              resultCard.classList.add("search-card");

              resultCard.innerHTML = `
                                <img src="${result.image}" alt="${result.title}">
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

    function getSearchResults(searchTerm) {
      const sampleCourses = [
        {
          title: "JavaScript Basics",
          description: "Learn the fundamentals of JavaScript.",
          image: "course.png",
          link: "#",
        },
        {
          title: "HTML & CSS",
          description: "Master web development with HTML and CSS.",
          image: "image.png",
          link: "#",
        },
        {
          title: "HTML & CSS",
          description: "Master web development with HTML and CSS.",
          image: "course.png",
          link: "#",
        },
        {
          title: "React for Beginners",
          description: "Introduction to building UI with React.",
          image: "image.png",
          link: "#",
        },
        {
          title: "Python for Data Science",
          description: "Dive into data analysis with Python.",
          image: "course.png",
          link: "#",
        },
        {
          title: "Python for Data Science",
          description: "Dive into data analysis with Python.",
          image: "image.png",
          link: "#",
        },
      ];
      return sampleCourses.filter((course) =>
        course.title.toLowerCase().includes(searchTerm)
      );
    }

    document.addEventListener("click", (event) => {
      if (!event.target.closest(".search-box")) {
        searchResults.classList.remove("show");
      }
    });



  });


     // course details
     function showSection(sectionId, clickedButton) {
        // Hide all sections
        document.querySelectorAll("#course-deatils-view-1 > div").forEach(section => {
            section.style.display = "none";
        });

        // Show the selected section
        document.getElementById(sectionId).style.display = "block";

        // Remove "active" class from all buttons
        document.querySelectorAll("#section-header button").forEach(button => {
            button.classList.remove("active");
        });

        // Add "active" class to clicked button
        clickedButton.classList.add("active");
    }

    // Set "Overview" as active by default
    document.querySelector("#section-header button").classList.add("active");














    document.addEventListener("DOMContentLoaded", function () {


      // Fetch and apply the saved completion statuses
// Fetch and apply the saved completion statuses
    function fetchDataLesson(){
    fetch('/get-completions')
    .then(response => response.json())
    .then(completions => {
        let completedCount = 0;
        const totalLessons =  completions.length;

        completions.forEach(lesson => {
            const videoElement = document.querySelector(`[data-lesson-id="${lesson.lessonId}"]`);
            if (videoElement && lesson.completed) {
                complete(videoElement);
                checkCompletion(videoElement);
                videoElement.dataset.completed = "true"; // Persist visually
                completedCount++;
            }
        });

        // Calculate and update progress
        const progressPercentage = totalLessons > 0 ? (completedCount / totalLessons) * 100 : 0;
        updateProgress(progressPercentage);
    })
    .catch(error => console.error("Error fetching completions:", error));

   // Progress bar logic
   const progressBar = document.getElementById('myProgressBar');
   const progressLabel = document.getElementById('progressLabel');
   let progress = 0;

  function updateProgress(value) {
    progress = Math.max(0, Math.min(100, value)); // Clamp between 0 and 100
    progressBar.style.width = progress + '%';
    progressLabel.textContent = Math.round(progress) + '%';
  }
    }
    // call the fucntion to fetch dta
    fetchDataLesson();


  
      const videoPlaye = document.getElementById("videos-to-play");
      const videoLinks = document.querySelectorAll(".video-link");
      const mainVideo = document.getElementById("main-video");
      const videoTitle = document.getElementById("video-title");
      const videoDesc = document.getElementById("videos-lesson-description");
  
      var currentTime = 0;
      var duration = 0;
      var video = null;
  
      videoLinks.forEach(link => {
          link.addEventListener("click", function (e) {
              e.preventDefault();
  
              // Reset tracking
              currentTime = 0;
              duration = 0;
              video = this;
  
              // Show player
              videoPlaye.style.display = "block";
  
              // Get video details from data attributes
              const newSrc = this.getAttribute("data-src");
              const newTitle = this.getAttribute("data-title");
              const newDesc = this.getAttribute("data-desc");
  
              // Update video source and metadata
              mainVideo.src = newSrc;
              videoTitle.textContent = newTitle;
              videoDesc.textContent = newDesc;
  
              // Scroll up to the video player
              videoPlaye.scrollIntoView({ behavior: "smooth" });
  
              // Play video automatically
              mainVideo.play();
          });
      });
  


      // Track video progress
      mainVideo.addEventListener("timeupdate", function () {
          if (!video) return;
  
          currentTime = Math.floor(mainVideo.currentTime);
          duration = Math.floor(mainVideo.duration);
          var percentage = ((currentTime / duration) * 100).toFixed(2);
  
          if (percentage > 10 && video.dataset.completed !== "true") {
              markLessonComplete(video);
          }
      });


  
      // Function to mark lesson as completed
      function markLessonComplete(video) {
          complete(video);
          checkCompletion(video);

          if(video.dataset.completed !== "true") {

            video.setAttribute("data-completed", "true");  // Prevent multiple triggers


            const lessonId = video.getAttribute("data-lesson-id");
            fetch('/update-completion', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ lessonId: lessonId, completed: true })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    console.log("Lesson marked as completed:", data);
                } else {
                    console.error("Failed to update lesson completion.");
                    video.dataset.completed = "false"; // Reset if failed
                }
            })
            .catch(error => {
                console.error("Error updating completion:", error);
                video.dataset.completed = "false"; // Reset on error
            });

            fetchDataLesson();


          }else{
            console.log("Lesson already marked as completed");
          }
       
        }






  
      // Visually mark lesson as completed
      function complete(video) {
          var firstChild = video.firstElementChild;
          var firstElemOfFirstChild = firstChild.firstElementChild;
          firstElemOfFirstChild.style.color = "green";
          firstElemOfFirstChild.className = "bx bx-check-circle";
      }
  


      // Check if all lessons in a section are completed
      function checkCompletion(video) {
          var parentElem = video.parentElement;
          var children = parentElem.children;
          var count = 0;
  
          for (var i = 0; i < children.length; i++) {
              var firstChild = children[i].firstElementChild;
              var firstElemOfFirstChild = firstChild.firstElementChild;
              if (firstElemOfFirstChild.style.color === "green") {
                  count++;
              }
          }
  
          if (count === children.length) {
              var parentElemenetOfParent = parentElem.parentElement;
              var firstChildOfParent = parentElemenetOfParent.firstElementChild;
              firstChildOfParent.style.color = "green";
              var iconOfFirstChild = firstChildOfParent.firstElementChild;
              iconOfFirstChild.className = "bx bx-check-circle";
              iconOfFirstChild.style.color = "green";
          }
      }








      // ... (Previous JavaScript code for "Read More" and star rating) ...

const reviewForm = document.querySelector('.review-form');

reviewForm.addEventListener('submit', function(event) {
  event.preventDefault(); // Prevent default form submission

  const name = this.querySelector('input[type="text"]').value;
  const email = this.querySelector('input[type="email"]').value;
  const reviewText = this.querySelector('textarea').value;
  const rating = this.querySelector('input[name="rating"]').value; // Get rating from hidden input

  // Basic client-side validation (you should also validate on the server)
  if (!name || !email || !reviewText || !rating) {
    alert("Please fill in all fields and select a rating.");
    return;
  }

  const newReview = {
    name: name,
    email: email,
    review: reviewText,
    rating: rating
  };

  // Send data to the server using fetch (or XMLHttpRequest)
  fetch('/your-server-endpoint', { // Replace with your server endpoint
    method: 'POST',
    headers: {
      'Content-Type': 'application/json' // Important: tell the server the data format
    },
    body: JSON.stringify(newReview) // Convert the object to JSON
  })
  .then(response => {
    if (!response.ok) {
        return response.text().then(err => {throw new Error(err)});
    }
    return response.json();
  })
  .then(data => {
    // Handle successful submission (e.g., display a thank you message, clear the form)
    console.log('Review submitted successfully:', data);
    alert("Thank you for your review!");
    reviewForm.reset(); // Clear the form
    appendNewReview(newReview); // Append the review to the list
  })
  .catch(error => {
    // Handle errors (e.g., display an error message)
    console.error('Error submitting review:', error);
    alert("An error occurred while submitting your review. Please try again later.");
  });
});

function appendNewReview(review) {
  const reviewList = document.querySelector('.reviews-section'); // Or wherever your reviews are displayed

  const newReviewDiv = document.createElement('div');
  newReviewDiv.classList.add('review');

  newReviewDiv.innerHTML = `
    <div class="review-author">
      <img src="default-avatar.png" alt="Avatar">
      <span>${review.name}</span>
      <span class="review-date">Just now</span> </div>
    <div class="review-rating">
        ${getStars(parseInt(review.rating))}
    </div>
    <p class="review-text">${review.review}</p>
  `;

  reviewList.appendChild(newReviewDiv); // Add the new review to the top of the list
}

function getStars(rating) {
    let starsHtml = '';
    for (let i = 0; i < rating; i++) {
        starsHtml += '<i class="fas fa-star active"></i>';
    }
    for (let i = rating; i < 5; i++) {
        starsHtml += '<i class="fas fa-star"></i>';
    }
    return starsHtml;
}

      
  });


  