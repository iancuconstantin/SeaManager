import {getBasicAuthHeaders, getBearerAuthHeaders} from "./authUtils";

export const fetchBasicAuth = async (url, method, user, pass) => {
    const headers = getBasicAuthHeaders(user, pass);
    const response = await fetch(url, {
        method: method,
        headers: headers
    });
    return response;
};

export const fetchBearerAuth = async (url, method) => {
    console.log("IN FETCH BEARER")
    const headers = getBearerAuthHeaders();
    const response = await fetch(url, {
        method: method,
        headers: headers
    });
    return response;
};