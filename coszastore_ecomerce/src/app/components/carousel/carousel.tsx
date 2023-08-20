"use client"
import useEmblaCarousel, { EmblaOptionsType } from "embla-carousel-react";
import React, { PropsWithChildren, useEffect, useState } from "react";
import Dots from "./dots";
import CarouselControls from "./prev";

type Props = PropsWithChildren & EmblaOptionsType;

const Carousel = ({ children, ...options }: Props) => {
    const [emblaRef, emblaApi] = useEmblaCarousel(options);

    const [selectedIndex, setSelectedIndex] = useState(0);
    const length = React.Children.count(children);
    const canScrollNext = !!emblaApi?.canScrollNext();
    const canScrollPrev = !!emblaApi?.canScrollPrev();
    useEffect(() => {
        function selectHandler() {
            // selectedScrollSnap gives us the current selected index.
            const index = emblaApi?.selectedScrollSnap();
            setSelectedIndex(index || 0);
        }

        emblaApi?.on("select", selectHandler);
        // cleanup
        return () => {
            emblaApi?.off("select", selectHandler);
        };
    }, [emblaApi]);

    return (
        <>
            <div className="overflow-hidden" ref={emblaRef}>
                <div className="flex">{children}</div>
            </div>
            <Dots itemsLength={length} selectedIndex={selectedIndex} />
            <CarouselControls
                canScrollNext={canScrollNext}
                canScrollPrev={canScrollPrev}
                onNext={() => emblaApi?.scrollNext()}
                onPrev={() => emblaApi?.scrollPrev()}
            />
        </>
    );
};
export default Carousel;
