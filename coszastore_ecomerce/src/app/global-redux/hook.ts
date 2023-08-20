import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import type { RootState, DispatchType } from "./config-store";

export const useAppDispatch = () => useDispatch<DispatchType>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
