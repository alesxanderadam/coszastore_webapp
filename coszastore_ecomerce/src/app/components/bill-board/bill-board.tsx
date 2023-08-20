import type { NextPage } from "next";
import Image from "next/image";
import { items } from "@/mock-data.json"
import Carousel from "../carousel/carousel";
const BillBoard: NextPage = () => {
    return (
        <div className="sm:w-100 mx-auto">
            <Carousel loop>
                {items.bootstrap?.map((data: any, i: number) => {
                    console.log(data.imageUrl)
                    return (
                        <div className="relative h-[50.25vw] brightness-[60%] flex-[0_0_100%]" key={i}>
                            <Image src={data.imageUrl} fill className="object-cover" alt="alt" />
                        </div>
                    );
                })}
            </Carousel>
        </div>
    );
};

export default BillBoard;