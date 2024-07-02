// scr/pages/data/IcelandData.js
export const basicData = {
    name: "Enchanting Italy",
    country: "Italy",
    duration: 10,
    startprice: 1599,
    season: "Summer",
};

export const coverData = {
    image: "/assets/images/italy/image1.jpg", // Replace with actual image path
};

export const overview = {
    overview: "Embark on a 10-day summer adventure through Italy's most iconic cities and regions. From the romantic canals of Venice to the historical wonders of Rome, the artistic treasures of Florence, the picturesque landscapes of Tuscany, and the stunning Amalfi Coast, this tour offers a captivating blend of art, history, culture, and natural beauty.",
    highlights: [
        "Venice: Gondola rides, St. Mark's Square, Doge's Palace, Murano glass.",
        "Rome: Colosseum, Trevi Fountain, Vatican City, Spanish Steps, Sistine Chapel.",
        "Florence: Uffizi Gallery, Duomo, Ponte Vecchio, Michelangelo's David.",
        "Tuscany: Chianti wine region, rolling hills, medieval towns.",
        "Amalfi Coast: Positano, Ravello, scenic drives, boat trips."
    ]
};

export const itineraryData = [
    {
        day: 1,
        lat: 45.437181,
        lng: 12.334590,
        id: 'detailDay1',
        title: "Day 1: Arrival in Venice",
        details: [
            "Morning: Arrive at Venice Marco Polo Airport and take a water taxi to your hotel.",
            "Lunch: Trattoria alla Madonna (Recommended dishes: seafood pasta, risotto, tiramisu).",
            "Afternoon: Explore St. Mark's Square, visit St. Mark's Basilica, and take a gondola ride.",
            "Dinner: Osteria Al Squero (Recommended dishes: cicchetti, seafood, local wines).",
            "Accommodation: Hotel Danieli, a Luxury Collection Hotel, Venice."
        ]
    },
    {
        day: 2,
        lat: 45.437181,
        lng: 12.334590,
        id: 'detailDay2',
        title: "Day 2: Exploring Venice",
        details: [
            "Morning: Visit Doge's Palace and Bridge of Sighs.",
            "Lunch: Dal Moro's Fresh Pasta to Go (Recommended dishes: pasta with various sauces).",
            "Afternoon: Take a boat trip to Murano Island, known for its glassblowing.",
            "Dinner: Antiche Carampane (Recommended dishes: seafood, Venetian specialties).",
            "Accommodation: Hotel Danieli, a Luxury Collection Hotel, Venice."
        ]
    },
    {
        day: 3,
        lat: 43.769823,
        lng: 11.255586,
        id: 'detailDay3',
        title: "Day 3: Journey to Florence",
        details: [
            "Morning: Take a high-speed train from Venice to Florence.",
            "Lunch: All'antico Vinaio (Recommended dishes: paninis with various fillings).",
            "Afternoon: Check into your hotel and explore the Piazza del Duomo.",
            "Dinner: Trattoria Za Za (Recommended dishes: Florentine steak, pasta, Tuscan wines).",
            "Accommodation: Hotel Brunelleschi, Florence."
        ]
    },
    {
        day: 4,
        lat: 43.769823,
        lng: 11.255586,
        id: 'detailDay4',
        title: "Day 4: Discovering Florence",
        details: [
            "Morning: Visit the Uffizi Gallery and see masterpieces by Botticelli, Michelangelo, and Leonardo da Vinci.",
            "Lunch: Mercato Centrale (Recommended: try various food stalls offering local specialties).",
            "Afternoon: Walk across the Ponte Vecchio, visit the Pitti Palace, and climb the Duomo for panoramic views.",
            "Dinner: La Buchetta (Recommended dishes: ribollita, bistecca alla fiorentina).",
            "Accommodation: Hotel Brunelleschi, Florence."
        ]
    },
    {
        day: 5,
        lat: 43.317551,
        lng: 11.783033,
        id: 'detailDay5',
        title: "Day 5: Tuscan Countryside",
        details: [
            "Morning: Pick up your rental car and drive through the Chianti wine region.",
            "Lunch: Ristorante Dario Cecchini (Recommended: Tuscan cuisine, especially meat dishes).",
            "Afternoon: Visit the medieval town of San Gimignano.",
            "Dinner: Enjoy a traditional Tuscan dinner at a farmhouse in the countryside.",
            "Accommodation: Agriturismo Poggio Istiano, Siena."
        ]
    },
    {
        day: 6,
        lat: 41.890210,
        lng: 12.492231,
        id: 'detailDay6',
        title: "Day 6: Arrival in Rome",
        details: [
            "Morning: Drive from Siena to Rome.",
            "Lunch: Armando al Pantheon (Recommended dishes: cacio e pepe, saltimbocca, tiramisu).",
            "Afternoon: Check into your hotel and explore the Trevi Fountain.",
            "Dinner: La Taverna dei Fori Imperiali (Recommended dishes: pasta carbonara, Roman artichokes).",
            "Accommodation: The Inn at the Roman Forum, Rome."
        ]
    },
    {
        day: 7,
        lat: 41.890210,
        lng: 12.492231,
        id: 'detailDay7',
        title: "Day 7: Exploring Rome",
        details: [
            "Morning: Visit the Colosseum and Roman Forum.",
            "Lunch: Pizzeria da Remo (Recommended dishes: Roman-style pizza).",
            "Afternoon: Explore Vatican City, including St. Peter's Square and St. Peter's Basilica.",
            "Dinner: Il Margutta RistorArte (Vegetarian fine dining, recommended for a special occasion).",
            "Accommodation: The Inn at the Roman Forum, Rome."
        ]
    },
    {
        day: 8,
        lat: 41.890210,
        lng: 12.492231,
        id: 'detailDay8',
        title: "Day 8: More of Rome",
        details: [
            "Morning: Visit the Pantheon and Spanish Steps.",
            "Lunch: Pastificio Guerra (Recommended dishes: fresh pasta).",
            "Afternoon: Explore the Trastevere neighborhood and enjoy gelato.",
            "Dinner: Roscioli Salumeria con Cucina (Recommended dishes: charcuterie, pasta).",
            "Accommodation: The Inn at the Roman Forum, Rome."
        ]
    },
    {
        day: 9,
        lat: 40.851774,
        lng: 14.268116,
        id: 'detailDay9',
        title: "Day 9: Amalfi Coast",
        details: [
            "Morning: Take a train to Naples and then a ferry to Positano.",
            "Lunch: Da Adolfo (Recommended dishes: seafood, grilled fish).",
            "Afternoon: Relax on the beach or take a boat trip along the Amalfi Coast.",
            "Dinner: Ristorante Max (Recommended dishes: seafood, pasta with seafood).",
            "Accommodation: Le Sirenuse, Positano."
        ]
    },
    {
        day: 10,
        lat: 40.851774,
        lng: 14.268116,
        id: 'detailDay10',
        title: "Day 10: Departure from Naples",
        details: [
            "Morning: Enjoy breakfast with a view in Positano.",
            "Lunch: La Sponda (Recommended dishes: Mediterranean cuisine, seafood).",
            "Afternoon: Take a ferry back to Naples and depart from Naples International Airport."
        ]
    }
];

export const bookingInfo = {
    included: [
        "Direct flights from Brussels to Venice and from Naples to Brussels with ITA Airways (incl. 23 kg luggage)",
        "Accommodation for 9 nights in various high-rated hotels (mix of 3-star and 4-star)",
        "Daily breakfast at your hotel",
        "High-speed train tickets between Venice and Florence",
        "Rental car for 2 days (Fiat 500 or similar) with unlimited mileage, CDW, theft protection, and VAT",
        "Car pick-up/drop-off in Siena and return in Rome",
        "Our exclusive Italy Travel Guide app with offline maps and recommendations",
        "24/7 customer support hotline in English",
        "All local taxes and fees"
    ],
    notIncluded: [
        "Comprehensive car insurance (excess reduction, tire/glass coverage, etc.)",
        "Travel insurance (strongly recommended)",
        "Fuel for the rental car",
        "Tolls and parking fees",
        "Entry fees to museums, attractions, and historical sites",
        "Meals and drinks not specified in the itinerary",
        "Personal expenses and gratuities"
    ]
};

export const practicalInfo = {
    travelDocuments: [
        "EU citizens (including Belgians) need a valid passport or national ID card.",
        "Non-EU citizens should check the visa requirements for Italy. A valid passport is always required."
    ],
    insurances: [
        "We strongly recommend purchasing travel insurance to cover unexpected events like trip cancellation, medical emergencies, or lost luggage."
    ],
    tailoredTravel: [
        "We can personalize this itinerary to your interests and preferences. Contact us for a tailor-made Italy experience.",
        "Group discounts available for parties of 8 or more."
    ]
};
