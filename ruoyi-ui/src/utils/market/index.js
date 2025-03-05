import {listMarketByAsync} from "@/api/market/market";

export async function getMarketList() {

  let item = localStorage.getItem("marketList");
  if (item == null) {
    let marketList = [];
    let rows = await listMarketByAsync({status: 1});
    rows.forEach((row) => {
      marketList.push({
        id: row.id,
        name: row.name,
        currency: row.currency,
      })
    })
    localStorage.setItem("marketList", JSON.stringify(marketList))
    return marketList;
  }
  return JSON.parse(item);

}

export function clearMarket() {
  localStorage.removeItem("marketList");
}
