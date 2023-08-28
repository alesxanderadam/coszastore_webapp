import NextJsCarousel from "./components/bill-board/bill-board";
import Header from "./components/header/header";
import SideBar from "@/app/components/sidebar/side-bar";

export default function Home() {
  return (
      <>
        <NextJsCarousel />
        <div className='h-[800px]'></div>
      </>
  )
}
