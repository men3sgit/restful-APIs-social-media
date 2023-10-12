import ListGroup from "./components/ListGroup";
const cities = [
  "Hanoi",
  "Da Nang",
  "Sydney",
  "Bangkok",
  "Mumbai",
  "New Deli",
  "Moscow",
  "London",
  "Liver Pool",
  "Manchester",
  "New York",
  "Tokyo",
];
const heading = "MY FAVORITE CITY";
function App() {
  return (
    <>
      <ListGroup items={cities} heading={heading} /> 
    </>)
}
export default App;
