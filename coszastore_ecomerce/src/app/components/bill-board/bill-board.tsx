import type { NextPage } from "next";
import Image from "next/image";
import { items } from "@/mock-data.json"
import Carousel from "../carousel/carousel";
const BillBoard: NextPage = () => {
    return (
        <div className="sm:w-100">
            <Carousel loop>
                {items.bootstrap?.map((data: any, i: number) => {
                    return (
                        <div className="relative h-[50.24vw] flex-[0_0_100%]" key={i}>
                            <Image loading="eager" src={data.imageUrl} fill={true} className="object-cover" alt="alt" />
                        </div>
                    );
                })}
            </Carousel>
        </div>
    );
};

export default BillBoard;
