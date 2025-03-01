
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