using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Text;

// .NET console app for Restful Booker API demo

using var http = new HttpClient { BaseAddress = new Uri("https://restful-booker.herokuapp.com/") };
http.DefaultRequestHeaders.Accept.Clear();
http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
http.DefaultRequestHeaders.UserAgent.ParseAdd("RestfulBookerClient/1.0 (+https://example.local)");

// --- POST /auth
var authResp = await http.PostAsync("auth", new StringContent(JsonSerializer.Serialize(new { username = "admin", password = "password123" }), Encoding.UTF8, "application/json"));
authResp.EnsureSuccessStatusCode();
var authText = await authResp.Content.ReadAsStringAsync();
string token;
try
{
    token = JsonDocument.Parse(authText).RootElement.GetProperty("token").GetString()!;
}
catch (Exception)
{
    Console.WriteLine("Auth response was not JSON. Body:\n" + authText);
    throw;
}
Console.WriteLine($"Token: {token}\n");

// --- POST /booking
var bookingPayload = new
{
    firstname = "Ada",
    lastname = "Lovelace",
    totalprice = 199,
    depositpaid = true,
    bookingdates = new { checkin = DateTime.UtcNow.AddDays(7).ToString("yyyy-MM-dd"), checkout = DateTime.UtcNow.AddDays(10).ToString("yyyy-MM-dd") },
    additionalneeds = "Breakfast"
};
var createResp = await http.PostAsync("booking", new StringContent(JsonSerializer.Serialize(bookingPayload), Encoding.UTF8, "application/json"));
createResp.EnsureSuccessStatusCode();
var createText = await createResp.Content.ReadAsStringAsync();
int bookingId;
try
{
    bookingId = JsonDocument.Parse(createText).RootElement.GetProperty("bookingid").GetInt32();
}
catch (Exception)
{
    Console.WriteLine("Create response was not JSON. Body:\n" + createText);
    throw;
}
Console.WriteLine($"Booking created with ID: {bookingId}\n");

// --- GET /booking/{id}
var getResp = await http.GetAsync($"booking/{bookingId}");
getResp.EnsureSuccessStatusCode();
var getText = await getResp.Content.ReadAsStringAsync();
Console.WriteLine("Fetched Booking:");
Console.WriteLine(getText);
