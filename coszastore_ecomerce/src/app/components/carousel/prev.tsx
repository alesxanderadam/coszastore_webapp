// components/CarouselControls.tsx
import classNames from "classnames";

type Props = {
    canScrollPrev: boolean;
    canScrollNext: boolean;
    onPrev(): void;
    onNext(): void;
};
const CarouselControls = (props: Props) => {
    return (
        <div className="flex justify-between gap-2 absolute top-[50%] w-full">
            <button
                onClick={() => {
                    if (props.canScrollPrev) {
                        props.onPrev();
                    }
                }}
                disabled={!props.canScrollPrev}
                className={classNames({
                    "px-4 py-2 text-white rounded-md embla__prev": true,
                    "bg-indigo-200": !props.canScrollPrev,
                    "bg-indigo-400": props.canScrollPrev,
                })}
            >
                Prev
            </button>
            <button
                onClick={() => {
                    if (props.canScrollNext) {
                        props.onNext();
                    }
                }}
                disabled={!props.canScrollNext}
                className={classNames({
                    "px-4 py-2 text-white rounded-md embla__next": true,
                    "bg-indigo-200": !props.canScrollNext,
                    "bg-indigo-400": props.canScrollNext,
                })}
            >
                Next
            </button>
        </div>
    );
};
export default CarouselControls;
