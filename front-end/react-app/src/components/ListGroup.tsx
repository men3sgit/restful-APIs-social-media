import { useState } from "react";
interface Props {
  items: string[];
  heading: string;
}

function ListGroup({ items, heading }: Props) {
  const [selectedIndex, setSelectedIndex] = useState(-1);
  return (
    <>
      <h1>{heading}</h1>
      <ul className="list-group">
        {items.length === 0 && <li>No item:</li>}
        {items.map((city, index) => (
          <li
            key={city}
            className={
              index === selectedIndex
                ? "list-group-item active"
                : "list-group-item"
            }
            onClick={() => {
              setSelectedIndex(index);
              console.log(city);
            }}
          >
            {city}
          </li>
        ))}
      </ul>
    </>
  );
}
export default ListGroup;
