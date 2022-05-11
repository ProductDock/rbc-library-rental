db.createUser(
    {
        user: "root1",
        pwd: "root1",
        roles:[
        {
            role :"readWrite",
            db: "rental-db"
        }]
    }
);

db["rental-record"].insert([
  {
    bookId: "2",
    interactions: [
      {
        userEmail: "adriana.martinovic@productdock.com",
        date: "October 29, 2019, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "4",
    interactions: [
      {
        userEmail: "jovanka.bobic@productdock.com",
        date: "December 15, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "6",
    interactions: [
      {
        userEmail: "jovana.minic@productdock.com",
        date: "August 5, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "7",
    interactions: [
      {
        userEmail: "marko.radinovic@productdock.com",
        date: "March 14, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "30",
    interactions: [
      {
        userEmail: "nemanja.vasiljevic@productdock.com",
        date: "March 14, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "36",
    interactions: [
      {
        userEmail: "jovanka.bobic@productdock.com",
        date: "October 16, 2020, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "38",
    interactions: [
      {
        userEmail: "dragana.bogdanovic@productdock.com",
        date: "March 29, 2020, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "40",
    interactions: [
      {
        userEmail: "dragana.bogdanovic@productdock.com",
        date: "March 29, 2020, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "52",
    interactions: [
      {
        userEmail: "bojan.ristic@productdock.com",
        date: "May 24, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "55",
    interactions: [
      {
        userEmail: "nikola.lajic@productdock.com",
        date: "November 1, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "57",
    interactions: [
      {
        userEmail: "suzana.bogojevic@productdock.com",
        date: "September 15, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "60",
    interactions: [
      {
        userEmail: "jovana.minic@productdock.com",
        date: "August 25, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "63",
    interactions: [
      {
        userEmail: "andrija.vujasinovic@productdock.com",
        date: "November 5, 2021, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "65",
    interactions: [
      {
        userEmail: "adriana.martinovic@productdock.com",
        date: "March 4, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "66",
    interactions: [
      {
        userEmail: "bojana.armacki@productdock.com",
        date: "January 12, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "67",
    interactions: [
      {
        userEmail: "nemanja.vasiljevic@productdock.com",
        date: "January 19, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "69",
    interactions: [
      {
        userEmail: "milica.zivkov@productdock.com",
        date: "March 2, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
  {
    bookId: "70",
    interactions: [
      {
        userEmail: "jaroslav.slivka@productdock.com",
        date: "March 2, 2022, 2:22:22 PM",
        status: "RENTED",
      },
    ],
  },
]);