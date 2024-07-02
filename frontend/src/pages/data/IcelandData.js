// scr/pages/data/IcelandData.js
export const basicData = {
    name: "Mystical Iceland",
    country: "Iceland",
    duration: 6,
    startprice: 1899,
    season: "Summer"
};

export const coverData = {
    image: "/assets/images/iceland/image1.jpg", // Replace with actual image path
};

export const overview = {
    overview: "Experience the magic of Iceland during this 6-day summer tour. Witness the captivating Northern Lights, explore glaciers, hike on volcanoes, soak in geothermal hot springs, and immerse yourself in the unique landscapes of this extraordinary country.",
    highlights: [
        "Reykjavík: Hallgrímskirkja, Harpa Concert Hall, colorful houses, whale watching.",
        "Golden Circle: Þingvellir National Park, Gullfoss waterfall, Geysir geothermal area.",
        "South Coast: Seljalandsfoss and Skógafoss waterfalls, black sand beaches, Jökulsárlón glacier lagoon.",
        "Blue Lagoon: Relax in the geothermal spa famous for its milky-blue water and healing properties.",
        "Northern Lights (Aurora Borealis): Witness the enchanting natural light display in the night sky (seasonal)."
    ]
};

export const itineraryData = [
    {
        day: 1,
        lat: 64.128288,
        lng: -21.827774,
        id: 'detailDay1',
        title: "Day 1: Arrival in Reykjavík",
        details: [
            "Morning: Arrive at Keflavík International Airport and pick up your rental car.",
            "Lunch: Icelandic Fish and Chips (Recommended dishes: fresh cod or haddock, chips, tartar sauce).",
            "Afternoon: Explore Reykjavík city center, visit Hallgrímskirkja church, and wander along the harbor.",
            "Dinner: Sægreifinn (The Sea Baron) (Recommended dishes: lobster soup, grilled fish skewers).",
            "Accommodation: Hotel Borg by Keahotels."
        ]
    },
    {
        day: 2,
        lat: 64.128288,
        lng: -21.827774,
        id: 'detailDay2',
        title: "Day 2: Golden Circle",
        details: [
            "Morning: Embark on the Golden Circle route. Visit Þingvellir National Park, a UNESCO World Heritage Site.",
            "Lunch: Friðheimar (Tomato farm, recommended dishes: tomato soup, homemade bread).",
            "Afternoon: Witness the powerful Gullfoss waterfall and the erupting Geysir geothermal area.",
            "Dinner: Grillmarkaðurinn (Recommended dishes: Icelandic lamb, grilled seafood).",
            "Accommodation: Hotel Borg by Keahotels."
        ]
    },
    {
        day: 3,
        lat: 63.629771,
        lng: -19.017079,
        id: 'detailDay3',
        title: "Day 3: South Coast Adventure",
        details: [
            "Morning: Drive along the South Coast, stopping at Seljalandsfoss and Skógafoss waterfalls.",
            "Lunch: Súpurnar (Soup Company) (Recommended dishes: Icelandic lamb soup, seafood soup).",
            "Afternoon: Walk on the black sand beach of Reynisfjara and see the basalt columns.",
            "Evening: Visit the Jökulsárlón glacier lagoon and witness floating icebergs.",
            "Dinner: Pakkhús Restaurant (Recommended dishes: langoustine, lamb shank).",
            "Accommodation: Hótel Skaftafell."
        ]
    },
    {
        day: 4,
        lat: 63.878515,
        lng: -22.445309,
        id: 'detailDay4',
        title: "Day 4: Glacier Hike and Blue Lagoon",
        details: [
            "Morning: Join a guided glacier hike on Sólheimajökull glacier.",
            "Lunch: Volcano Huts (Recommended dishes: Icelandic meat soup, burgers).",
            "Afternoon: Relax and rejuvenate in the warm, milky-blue waters of the Blue Lagoon.",
            "Dinner: Lava Restaurant (Recommended dishes: seafood, lamb, vegetarian options).",
            "Accommodation: Silica Hotel at Blue Lagoon."
        ]
    },
    {
        day: 5,
        lat: 64.128288,
        lng: -21.827774,
        id: 'detailDay5',
        title: "Day 5: Reykjavík and Northern Lights",
        details: [
            "Morning: Return to Reykjavík and explore the city's museums and art galleries.",
            "Lunch: Bæjarins Beztu Pylsur (Recommended dish: Icelandic hot dog).",
            "Afternoon: Join a whale watching tour from the harbor.",
            "Evening: If conditions allow, embark on a Northern Lights hunt away from the city lights.",
            "Dinner: Kopar Restaurant Bar (Recommended dishes: seafood, lamb, vegetarian options).",
            "Accommodation: Hotel Borg by Keahotels."
        ]
    },
    {
        day: 6,
        lat: 64.128288,
        lng: -21.827774,
        id: 'detailDay6',
        title: "Day 6: Departure from Reykjavík",
        details: [
            "Morning: Enjoy a final breakfast in Reykjavík and do some last-minute souvenir shopping.",
            "Afternoon: Return the rental car at Keflavík International Airport and catch your flight home."
        ]
    }
];

export const bookingInfo = {
    included: [
        "Round-trip flights from Brussels to Reykjavík with Icelandair (incl. 23 kg luggage)",
        "Accommodation for 5 nights in comfortable hotels",
        "Daily breakfast at hotels",
        "Rental car for 6 days with unlimited mileage, CDW, theft protection, and VAT",
        "Guided glacier hike on Sólheimajökull",
        "Entrance to Blue Lagoon with towel and silica mud mask",
        "Our exclusive Iceland Travel Guide app",
        "24/7 emergency assistance in Iceland",
        "All local taxes and fees"
    ],
    notIncluded: [
        "Optional activities not mentioned in the itinerary",
        "Meals and drinks not specified in the itinerary",
        "Personal expenses and gratuities",
        "Travel and medical insurance (highly recommended)"
    ]
};

export const practicalInfo = {
    travelDocuments: [
        "EU citizens (including Belgians) need a valid passport or national ID card.",
        "Non-EU citizens should check visa requirements for Iceland."
    ],
    insurances: [
        "We highly recommend purchasing travel insurance to cover unexpected events such as trip cancellation, medical emergencies, or lost luggage."
    ],
    tailoredTravel: [
        "We can customize this itinerary to fit your interests and preferences. Contact us for a personalized Iceland adventure."
    ]
};
