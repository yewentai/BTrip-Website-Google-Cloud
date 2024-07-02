// scr/pages/HomePage.js

import React from 'react';
import { Carousel } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css';
import { carouselData } from './data/HomePageData';
import { Helmet } from 'react-helmet';

function HomePage() {
    const shuffledData = shuffleArray(carouselData);
    const slidesToShow = shuffledData.slice(0, 5); // Take the first 5 randomly selected slides

    function shuffleArray(array) {
        // Fisher-Yates Shuffling Algorithm
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]]; // Swapping elements
        }
        return array;
    }

    return (
        <div className="carousel-container">
            <Helmet>
                <title>BTrip - Home</title>  {/* Set your desired title here */}
                <link rel="icon" type="image/x-icon" href="%PUBLIC_URL%/assets/logos/favicon.ico" />
            </Helmet>
            <Carousel>
                {slidesToShow.map((slide, index) => (
                    <Carousel.Item key={index}>
                        <img
                            className="d-block w-100"
                            src={slide.imageSrc}
                            alt={slide.altText}
                        />
                        <Carousel.Caption>
                            <h3>{slide.title}</h3>
                            <p>
                                {slide.description}
                                <br />
                                <Link to={slide.linkTo}>Learn more</Link>
                            </p>
                        </Carousel.Caption>
                    </Carousel.Item>
                ))}
            </Carousel>
        </div>
    );
}

export default HomePage;
