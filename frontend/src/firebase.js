import { initializeApp } from 'firebase/app';
import {
    getAuth,
    connectAuthEmulator,
    onAuthStateChanged,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
} from 'firebase/auth';

let firebaseConfig;
if (window.location.hostname === 'localhost') {
    firebaseConfig = {
        apiKey: 'AIzaSyBoLKKR7OFL2ICE15Lc1-8czPtnbej0jWY',
        projectId: 'demo-distributed-systems-kul',
    };
} else {
    firebaseConfig = {
        apiKey: 'AIzaSyAy8RJ7rgGIMhHCjYAxRc8BYANmlIpXsmM', // web api key
        authDomain: 'btripagency.firebaseapp.com',
        projectId: 'btripagency',
    };
}

const firebaseApp = initializeApp(firebaseConfig);
const auth = getAuth(firebaseApp);
try {
    auth.signOut();
} catch (err) { }

if (window.location.hostname === 'localhost') {
    connectAuthEmulator(auth, 'http://localhost:8082', { disableWarnings: true });
}

export { auth };
