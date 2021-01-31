"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.helloWorld = void 0;
const functions = require("firebase-functions");
// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript
exports.helloWorld = functions.https.onRequest((request, response) => {
    console.log('Why do you want to workout?');
    response.send("Hello from Firebase, Vivek is learning NodeJS - Like Arnold preached!");
});
//# sourceMappingURL=index.js.map