import {getAgentList, getAgentListByAsync} from "@/api/system/user";

export async function getAgent() {

  let item = localStorage.getItem("agentList");
  if (item == null) {
    let data = await getAgentListByAsync();
    console.log("getAgentList", data)
    localStorage.setItem("agentList", JSON.stringify(data))
    return data;
  }
  return JSON.parse(item);

}

export function clearAgent() {
  localStorage.removeItem("agentList");
}
