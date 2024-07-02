// scr/pages/DestinationPage.js
import React, { useState, useEffect, useRef } from 'react';
import mapboxgl from 'mapbox-gl';
import { Helmet } from 'react-helmet';

import BookingModal from '../components/BookingModal';

import './DestinationPage.css';
import 'mapbox-gl/dist/mapbox-gl.css';

const DestinationPage = ({ data }) => {
    const [activeSection, setActiveSection] = useState(''); // Correct destructuring
    const itineraryListRef = useRef(null);
    const [showModal, setShowModal] = useState(false);

    const handleBookNow = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    mapboxgl.accessToken = 'pk.eyJ1IjoiZmx5bWV0b3RoZW1vb24iLCJhIjoiY2x3dWp4dmF4MGR1MjJzczdoOXBmajlldSJ9.CHnxNUKyubYBmVuRkjVrTA';

    const Map = () => {
        useEffect(() => {
            const bounds = data.itineraryData.reduce(
                (bounds, day) => bounds.extend([day.lng, day.lat]),
                new mapboxgl.LngLatBounds()
            );

            const map = new mapboxgl.Map({
                container: 'map',
                style: 'mapbox://styles/mapbox/streets-v12',
                center: bounds.getCenter().toArray(), // Use the bounding box center as the initial center point
                zoom: 8 // Set a initial zoom level
            });

            map.scrollZoom.disable();

            map.fitBounds(bounds, { padding: 50 }); // Automatically adjust the view to include all marks

            const nav = new mapboxgl.NavigationControl({
                visualizePitch: true
            });
            map.addControl(nav, 'top-right');

            const handleScroll = (id) => {
                setActiveSection(id);
                const element = itineraryListRef.current.querySelector(`#${id}`);
                if (element) {
                    element.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'nearest' });
                }
            };


            // Loop through itineraryData and add markers
            data.itineraryData.forEach((day, index) => {
                const el = document.createElement('div');
                el.className = 'marker';
                el.innerHTML = `<span class="text">${day.day}</span>`;

                // Create marker instance
                const marker = new mapboxgl.Marker(el)
                    .setLngLat([day.lng, day.lat])
                    .addTo(map);

                // Add click event listener
                marker.getElement().addEventListener('click', () => {
                    handleScroll(day.id);
                });
            });

            // Add lines between markers
            map.on('load', () => {
                map.addSource('route', {
                    'type': 'geojson',
                    'data': {
                        'type': 'Feature',
                        'properties': {},
                        'geometry': {
                            'type': 'LineString',
                            'coordinates': data.itineraryData.map(day => [day.lng, day.lat])
                        }
                    }
                });

                map.addLayer({
                    'id': 'route',
                    'type': 'line',
                    'source': 'route',
                    'layout': {
                        'line-join': 'round',
                        'line-cap': 'round'
                    },
                    'paint': {
                        'line-color': '#3B82F6',
                        'line-width': 3,
                        'line-opacity': 0.75
                    }
                });
            });

            return () => map.remove();
        }, []);
        return <div id="map" style={{ width: '100%', height: '400px' }} />;
    };

    return (
        <main>
            {showModal && <BookingModal onClose={handleCloseModal} />}

            <Helmet>
                <title>{data.basicData.name}</title>  {/* Set your desired title here */}
                <link rel="icon" type="image/x-icon" href="%PUBLIC_URL%/assets/logos/favicon.ico" />
            </Helmet>

            <section id="cover" className="cover-section">
                <img src={data.coverData.image} alt={data.basicData.name} className="cover-image" />
                <div className="cover-content">
                    <h1 className="cover-title">{data.basicData.name}</h1>
                    <h3 className="cover-subtitle">from €{data.basicData.startprice}</h3>
                    <p className="cover-description">{data.basicData.season} Tour - {data.basicData.duration} days - Flight, rental car, and accommodation included</p>
                </div>
            </section>

            <section id="overview">
                <h2>Overview</h2>
                <p>{data.overview.overview}</p>
                <h3>Highlights</h3>
                <ul>
                    {data.overview.highlights.map((highlight, index) => (
                        <li key={index}>{highlight}</li>
                    ))}
                </ul>
                <div className="button-container">
                    <button className="btn" onClick={handleBookNow}>Book Now</button>
                </div>
            </section>

            <section id="itinerary">
                <h2>Itinerary</h2>
                <p>Explore the day-by-day activities and highlights of the tour.</p>
                <div className="itinerary-container">
                    <Map />
                    <div className="itinerary-list" ref={itineraryListRef}>
                        {data.itineraryData.map((item, index) => (
                            <div key={index} className={`day-detail ${activeSection === `detailDay${item.day}` ? 'active' : ''}`} id={`detailDay${item.day}`}>
                                <h3>{item.title}</h3>
                                {item.details.map((detail, idx) => (
                                    <p key={idx}>{detail}</p>
                                ))}
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            <section id="booking-info">
                <h2>Booking Information</h2>
                <h3>Included</h3>
                <ul>
                    {data.bookingInfo.included.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
                <h3>Not Included</h3>
                <ul>
                    {data.bookingInfo.notIncluded.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
            </section>

            <section id="practical-info">
                <h2>Practical Information</h2>
                <h3>Travel Documents</h3>
                <ul>
                    {data.practicalInfo.travelDocuments.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
                <h3>Insurances</h3>
                <ul>
                    {data.practicalInfo.insurances.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
                <h3>Tailored Travel</h3>
                <ul>
                    {data.practicalInfo.tailoredTravel.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
            </section>
        </main>
    );


};

export default DestinationPage;
