import NextJsCarousel from "./components/bill-board/bill-board";
import Header from "./components/header/header";

export default function Home() {
  return (
    <>
      <Header />
      <NextJsCarousel />
      <div className='h-[800px]'></div>
    </>
  )
}
